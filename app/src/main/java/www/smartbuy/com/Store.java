package www.smartbuy.com;

// this class will handle basic information about the store, like available countries, computers, etc.
public class Store {
    // Available countries
    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Canada"
    };

    // Available brands
    private static final String[] BRANDS = new String[]{
            "Dell", "HP", "Lenovo"
    };

    // Available computer types
    public enum ComputerTypes {
        Desktop,
        Laptop
    }

    // method to get the available countries
    public static String[] getCountries() {
        return COUNTRIES;
    }

    // method to get the available brands
    public static String[] getBrands() {
        return BRANDS;
    }
}
