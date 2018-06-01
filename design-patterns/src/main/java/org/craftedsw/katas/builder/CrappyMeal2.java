package org.craftedsw.katas.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 *
 */
public class CrappyMeal2 {

    public static class CrappyMealBuilder {
        private CrappyMeal2 meal;

        private CrappyMealBuilder() {
        }

        public static CrappyMealBuilder newInstance() {
            return new CrappyMealBuilder();
        }

        public CrappyMealBuilder givenMeal() {
            meal = new CrappyMeal2();
            return this;
        }

        public CrappyMealBuilder withBurger(Burger burger) {
            this.meal.addBurger(burger);
            return this;
        }

        public CrappyMealBuilder withFries() {
            this.meal.setFries(new Fries());
            return this;
        }

        public CrappyMealBuilder withDrink(Drink drink) {
            this.meal.setDrink(drink);
            return this;
        }

        public CrappyMealBuilder withDessert(Dessert dessert) {
            this.meal.setDessert(dessert);
            return this;
        }

        public CrappyMeal2 build() {
            return meal;
        }

        public List<CrappyMeal2> build(int number) {
            return Stream.generate(() -> build()).limit(number).collect(Collectors.toList());
        }

    }


    private List<Burger> burgers;
    private Fries fries;
    private Drink drink;
    private Dessert dessert;

    public CrappyMeal2() {
        this.burgers = new ArrayList<Burger>();
    }

    public CrappyMeal2(Burger burger) {
        this();
        addBurger(burger);
    }

    public CrappyMeal2(Burger burger, Fries fries) {
        this(burger);
        this.fries = fries;
    }
    //And so on...


    public void addBurger(Burger burger) {
        this.burgers.add(burger);
    }

    public List<Burger> getBurgers() {
        return burgers;
    }

    public Fries getFries() {
        return fries;
    }

    public void setFries(Fries fries) {
        this.fries = fries;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public Dessert getDessert() {
        return dessert;
    }

    public void setDessert(Dessert dessert) {
        this.dessert = dessert;
    }


    public static void main(String[] args) {
        CrappyMealBuilder builder = CrappyMealBuilder.newInstance();
        CrappyMeal2 meal1 = builder.givenMeal()
                .withBurger(new Burger())
                .withFries()
                .withDessert(new Dessert())
                .withDrink(new Drink())
                .build();

        CrappyMeal2 meal2 = builder.givenMeal()
                .withBurger(new Burger())
                .withDrink(new Drink())
                .build();

        System.out.println("list " + builder.build(4));

    }
}
