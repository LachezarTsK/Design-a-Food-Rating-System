
import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;

public class FoodRatings {

    private final Map<String, FoodData> foodNameToFoodData;
    private final Map<String, TreeSet<FoodData>> cuisineNameToSortedFoodData;

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        foodNameToFoodData = new HashMap<>();
        cuisineNameToSortedFoodData = new HashMap<>();

        for (int i = 0; i < foods.length; ++i) {
            FoodData current = new FoodData(foods[i], cuisines[i], ratings[i]);
            foodNameToFoodData.put(foods[i], current);
            cuisineNameToSortedFoodData.computeIfAbsent(cuisines[i], orderedFoodData -> new TreeSet<>()).add(current);
        }
    }

    public void changeRating(String food, int newRating) {
        FoodData toUpdate = foodNameToFoodData.get(food);
        cuisineNameToSortedFoodData.get(toUpdate.cuisine).remove(toUpdate);
        toUpdate.rating = newRating;
        cuisineNameToSortedFoodData.get(toUpdate.cuisine).add(toUpdate);
    }

    public String highestRated(String cuisine) {
        return cuisineNameToSortedFoodData.get(cuisine).first().name;
    }

}

class FoodData implements Comparable<FoodData> {

    String name;
    String cuisine;
    int rating;

    FoodData(String name, String cuisine, int rating) {
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
    }

    @Override
    public int compareTo(FoodData other) {
        if (this.rating == other.rating) {
            return this.name.compareTo(other.name);
        }
        return other.rating - this.rating;
    }
}
