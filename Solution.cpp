
#include <set>
#include <string>
#include <unordered_set>
using namespace std;

struct FoodData {
    string name;
    string cuisine;
    int rating{};

    FoodData() = default;
    FoodData(string name, string cuisine, int rating) : name{name}, cuisine{cuisine}, rating{rating}{}

    bool operator<(const FoodData& first) const {
        if (rating == first.rating) {
            return name < first.name;
        }
        return rating > first.rating;
    }
};

class FoodRatings {
    
    unordered_map<string, FoodData> foodNameToFoodData;
    unordered_map<string, set<FoodData>> cuisineNameToSortedFoodData;

public:
    FoodRatings(vector<string>& foods, vector<string>& cuisines, vector<int>& ratings) {
        for (size_t i = 0; i < foods.size(); ++i) {
            foodNameToFoodData.emplace(foods[i], FoodData(foods[i], cuisines[i], ratings[i]));
            cuisineNameToSortedFoodData[cuisines[i]].emplace(foods[i], cuisines[i], ratings[i]);
        }
    }

    void changeRating(const string& food, int newRating) {
        FoodData& toUpdate = foodNameToFoodData[food];
        cuisineNameToSortedFoodData[toUpdate.cuisine].erase(toUpdate);
        toUpdate.rating = newRating;
        cuisineNameToSortedFoodData[toUpdate.cuisine].insert(toUpdate);
    }

    string highestRated(const string& cuisine) const {
        return cuisineNameToSortedFoodData.at(cuisine).begin()->name;
    }
};
