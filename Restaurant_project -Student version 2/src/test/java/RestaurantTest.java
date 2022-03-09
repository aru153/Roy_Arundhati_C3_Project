//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    List<Item> menu = new ArrayList();
    
    //REFACTOR ALL THE REPEATED LINES OF CODE
    public void newRestaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        this.restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        this.restaurant.addToMenu("Sweet corn soup", 119);
        this.restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        this.newRestaurant();
        Restaurant spyRestaurant = Mockito.spy(restaurant);
        LocalTime currentTime = LocalTime.parse("11:30:00");
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(currentTime);
        assertTrue(spyRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        this.newRestaurant();
        Restaurant spyRestaurant = Mockito.spy(restaurant);
        LocalTime currentTime = LocalTime.parse("23:30:00");
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(currentTime);
        assertFalse(spyRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        this.newRestaurant();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        this.newRestaurant();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        this.newRestaurant();
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    @Test
    public void order_total_for_all_items_present() {
     this.newRestaurant();
     this.menu = this.restaurant.getMenu();
     Assertions.assertEquals(388, this.restaurant.getOrderTotal(this.menu));
    }

    @Test
    public void order_total_should_reduce_when_1_item_removed() {
     this.newRestaurant();
     this.menu = this.restaurant.getMenu();
     int total = this.restaurant.getOrderTotal(this.menu);
     int afterTotal = ((Item)this.menu.get(1)).getPrice();
     this.menu.remove(1);
     Assertions.assertEquals(total - afterTotal, this.restaurant.getOrderTotal(this.menu));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}
