#include <iostream>
#include <string>
#include <vector>
#include <iterator>
#include <fstream>
#include <sstream>
#include <cstdlib>
#include <set>
#include <map>
#include <algorithm>

using namespace std;

string RUN_NAME = "default";

struct Rec {
    long locId = 0;
    long imgId = 0;
    int clustId = 0;
    float relevance = 0.0;
};

ostream& operator<<(ostream& os, const Rec& dt)
{
    os << "locId=" << dt.locId << " imgId=" << dt.imgId << " clustId=" << dt.clustId << " rel=" << dt.relevance;
    return os;
}

ostream& operator<<(ostream& os, const vector<Rec>& dt)
{
//    os << "locId\timgId\tclustId\trel" << endl;
//    for (int i=0; i<dt.size(); ++i) {
//        os << dt[i].locId << "\t" << dt[i].imgId << "\t" << dt[i].clustId << "\t" << dt[i].relevance << endl;
//    }
//    os << "---";

    string w = " ";
    for (int i=0; i<dt.size(); ++i) {
        os << dt[i].locId << w;
        os << 0 << w;
        os << dt[i].imgId << w;
        os << i << w;
        os << (dt.size()-i)/(float)(dt.size()+1) << w;
        os << RUN_NAME << endl;
    }
    return os;
}

class CSVRow
{
public:
    std::string const& operator[](std::size_t index) const
    {
        return m_data[index];
    }
    std::size_t size() const
    {
        return m_data.size();
    }
    void readNextRow(std::istream& str)
    {
        std::string         line;
        std::getline(str,line);

        std::stringstream   lineStream(line);
        std::string         cell;

        m_data.clear();
        while(std::getline(lineStream,cell,';'))
        {
            m_data.push_back(cell);
        }
    }
private:
    std::vector<std::string>    m_data;
};

std::istream& operator>>(std::istream& str,CSVRow& data)
{
    data.readNextRow(str);
    return str;
}

class CSVIterator
{
public:
    typedef std::input_iterator_tag     iterator_category;
    typedef CSVRow                      value_type;
    typedef std::size_t                 difference_type;
    typedef CSVRow*                     pointer;
    typedef CSVRow&                     reference;

    CSVIterator(std::istream& str)  :m_str(str.good()?&str:NULL) { ++(*this); }
    CSVIterator()                   :m_str(NULL) {}

    // Pre Increment
    CSVIterator& operator++()               {if (m_str) { (*m_str) >> m_row;m_str = m_str->good()?m_str:NULL;}return *this;}
    // Post increment
    CSVIterator operator++(int)             {CSVIterator    tmp(*this);++(*this);return tmp;}
    CSVRow const& operator*()   const       {return m_row;}
    CSVRow const* operator->()  const       {return &m_row;}

    bool operator==(CSVIterator const& rhs) {return ((this == &rhs) || ((this->m_str == NULL) && (rhs.m_str == NULL)));}
    bool operator!=(CSVIterator const& rhs) {return !((*this) == rhs);}
private:
    std::istream*       m_str;
    CSVRow              m_row;
};

void loadClusterCsv(string filename, vector<Rec>& data, map<long,set<int>>& clustSetByLoc)
{
    ifstream stream(filename);
    for(CSVIterator loop(stream); loop != CSVIterator(); ++loop) {
        Rec r;
        r.locId = atol((*loop )[0].c_str());
        r.imgId = atol((*loop )[1].c_str());
        r.clustId = atoi((*loop )[2].c_str());
        data.push_back(r);
        clustSetByLoc[r.locId].insert(r.clustId);
    }
}

void loadRelevanceCsv2Cols(string filename, vector<Rec>& data)
{
    ifstream stream(filename);
    for(CSVIterator loop(stream); loop != CSVIterator(); ++loop) {
        long imgId = atol((*loop )[0].c_str());
        auto item = find_if(data.begin(), data.end(), [imgId](const Rec& r){ return r.imgId == imgId; });
        if (item != data.end()) {
            item->relevance = atof((*loop)[1].c_str());
        } else {
            cerr << "error: " << imgId << " is a nonexisting id" << endl;
        }
    }
}

void loadRelevanceCsv1Col(string filename, vector<Rec>& data)
{
    ifstream stream(filename);
    int cnt = 0;
    for(CSVIterator loop(stream); loop != CSVIterator(); ++loop) {
        data[cnt].relevance = atof((*loop)[0].c_str());
        ++cnt;
    }
}


map<long, vector<Rec>> splitByLoc(const vector<Rec>& data)
{
    map<long, vector<Rec>> result;
    for (const Rec& r : data) {
        result[r.locId].push_back(r);
    }
    return result;
}

float score(const vector<Rec>& order, int clustNum)
{
    float p = 0;
    set<int> clustSet;
    int cnt = 0;
    for (const Rec& item : order) {
        p += item.relevance;
        if (item.relevance > 0.001)
            clustSet.insert(item.clustId);
        cnt++;
    }
    p /= (float)cnt;
    float c = clustSet.size() / (float)clustNum;
    return 2*p*c/(p+c);
}

vector<Rec> reorder(vector<Rec>& data, int clustNum)
{
    vector<Rec> order;
    auto first = max_element(data.begin(), data.end(), [](Rec& a, Rec& b) { return a.relevance < b.relevance; });
    order.push_back(*first);
    data.erase(first);

    for (int i=0; i<50 && !data.empty(); ++i) {
        int idx = 0;
        float max = -1;
        for (int j=0; j<data.size(); ++j) {
            order.push_back(data[i]);
            float s = score(order, clustNum);
            if (max < s) {
                max = s;
                idx = j;
            }
            order.pop_back();
        }
        order.push_back(data[idx]);
        data.erase(data.begin() + idx);
    }

    return order;
}

int main(int argc, char *argv[])
{
    if (argc < 5) {
        cerr << "Usage: " << argv[0] << " <clusters csv> <relevance csv> <number of cols in relevance file = {1, 2}> <run name>" << endl;
        return -1;
    }

    string clustFilename(argv[1]);
    string relFilename(argv[2]);
    string relColNum(argv[3]);
    string runName(argv[4]);

    RUN_NAME = runName;

//    cout << clustFilename << endl;
//    cout << relFilename << endl;

    vector<Rec> data;
    map<long,set<int>> clustSetByLoc;
    loadClusterCsv(clustFilename, data, clustSetByLoc);

//    cout << "clusters loaded" << endl;
    if (relColNum == "2")
        loadRelevanceCsv2Cols(relFilename, data);
    else if (relColNum == "1")
        loadRelevanceCsv1Col(relFilename, data);
    else {
        cerr << "bad relevance file col number argument, should be '1' or '2'" << endl;
        return -2;
    }
//    cout << "relevance loaded" << endl;

    map<long, vector<Rec>> dataByLoc = splitByLoc(data);

    for (auto& p : clustSetByLoc) {
        long locId = p.first;
        int numClust = p.second.size();
        vector<Rec> newOrder = reorder(dataByLoc[locId], numClust);
        cout << newOrder;
    }

    return 0;
}