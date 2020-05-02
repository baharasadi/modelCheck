package models;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class ModelTests {
    private HashMap<String, String> informationCustomer = new HashMap<>();
    private HashMap<String, String> informationSeller = new HashMap<>();
    private HashMap<String, String> informationAdmin = new HashMap<>();

    public HashMap<String, String> addFirstCustomerInformation(HashMap<String, String> information) {
        information.put("username", "firstUser");
        information.put("password", "1234");
        information.put("name", "Joe");
        information.put("family name", "Bruce");
        information.put("email", "JoeBruce@gmail.com");
        information.put("number", "001234567");
        information.put("balance", "200");
        information.put("role", "customer");
        return information;
    }

    public HashMap<String, String> addSecondCustomerInformation(HashMap<String, String> information) {
        information.put("username", "secondUser");
        information.put("password", "127");
        information.put("name", "JayJay");
        information.put("family name", "Mars");
        information.put("email", "JayJayMars123@gmail.com");
        information.put("number", "010456321");
        information.put("balance", "300");
        information.put("role", "customer");
        return information;
    }

    public HashMap<String, String> addToSellerInformation(HashMap<String, String> information) {
        information.put("username", "sellerUser");
        information.put("password", "5678");
        information.put("name", "Jack");
        information.put("family name", "Fallon");
        information.put("email", "JackFallon@gmail.com");
        information.put("number", "001987654");
        information.put("balance", "100");
        information.put("role", "seller");
        information.put("company", "Best Products");
        return information;
    }
    public HashMap<String, String> addToAdminInformation(HashMap<String, String> information) {
        information.put("username", "adminUser");
        information.put("password", "5764");
        information.put("name", "Alex");
        information.put("family name", "Nick");
        information.put("email", "alexN@gmail.com");
        information.put("number", "001937654");
        information.put("balance", "43");
        information.put("role", "admin");
        return information;
    }

    private Admin testAdmin = new Admin(addToAdminInformation(informationAdmin));
    private Customer testCustomer = new Customer(addFirstCustomerInformation(informationCustomer));
    private Customer testCustomer2 = new Customer(addSecondCustomerInformation(informationCustomer));
    private Seller firstSeller = new Seller(addToSellerInformation(informationSeller));
    private Product firstProduct = new Product("1", "shoes", "NIKE", 123, firstSeller, 1, "clothing", "orthopedic shoes");
    private Product secondProduct = new Product("2", "sweater", "GAP", 280, firstSeller, 2, "clothing", "warm and cozy");
    private Cart newCart1 = testCustomer.newCartForThisCustomer();
    private Cart newCart2 = testCustomer2.newCartForThisCustomer();
    private Comment firstComment = new Comment("user", firstProduct, "First Comment", "The style was not really good.");
    private Comment secondComment = new Comment("person", firstProduct, "Second Comment", "I didn't buy it.It was not that good");
    private Rate firstRate = new Rate("firstUser", secondProduct, 4.5);
    private Rate secondRate = new Rate("secondUser", secondProduct, 4);
    private Discount firstDiscount = new Discount("123", new Date(2020, Calendar.APRIL, 17), new Date(2020, Calendar.MAY, 17), 0.3);
    private Request firstRequest = new Request("register seller");
    @Test
    public void addToCartTest() {
        newCart1.addProduct(firstProduct);
        newCart1.addProduct(secondProduct);
        newCart1.removeProduct(firstProduct);
        firstProduct.setSupply(firstProduct.getSupply() + 1);
        double actual = newCart1.calculateTotalPrice();
        Assert.assertEquals(newCart1.getProductsInCart(), Collections.singletonList(secondProduct));
        Assert.assertEquals(280, actual, 0.001);
        //BuyLog firstBuyLog = new BuyLog( new Date(), newCart1.getTotalPrice(), 0 , firstSeller, );
        SellLog firstLog = new SellLog(new Date(), newCart1.getTotalPrice() , 0, testCustomer);
        firstSeller.addToSellLogs(firstLog);
        secondProduct.addBuyer(testCustomer);
        Assert.assertEquals(newCart1.getProductsInCart(), Collections.singletonList(secondProduct));
        Assert.assertEquals(testCustomer, newCart1.getCustomer());
        newCart2.addProduct(firstProduct);
        newCart2.addProduct(secondProduct);
        firstProduct.addBuyer(testCustomer2);
        secondProduct.addBuyer(testCustomer2);
        SellLog secondLog = new SellLog(new Date(), newCart2.getTotalPrice(), 0, testCustomer2);
        firstSeller.addToSellLogs(secondLog);
        Assert.assertEquals(Collections.singletonList(testCustomer2), firstProduct.getThisProductsBuyers());
        Assert.assertEquals(Arrays.asList(testCustomer,testCustomer2), secondProduct.getThisProductsBuyers());
        Assert.assertEquals(0, firstProduct.getSupply());
        Assert.assertEquals(0, secondProduct.getSupply());
        Assert.assertEquals(Arrays.asList(firstLog, secondLog), firstSeller.getSellHistory());
    }


    @Test
    public void sellerProductTest(){
        Assert.assertEquals("Best Products", firstSeller.getCompany());
        firstProduct.addComment(firstComment);
        firstProduct.addComment(secondComment);
        String expectedTitle = "First Comment";
        Assert.assertEquals(expectedTitle, firstComment.getCommentTitle());
        String expectedBody = "The style was not really good.";
        Assert.assertEquals(expectedBody, firstComment.getCommentBody());
        Assert.assertEquals(Arrays.asList(firstComment, secondComment), firstProduct.getComments());
        secondProduct.addRate(firstRate);
        secondProduct.addRate(secondRate);
        double expectedAverageRate = (4 + 4.5) / 2;
        secondProduct.calculateAverageRate();
        Assert.assertEquals(expectedAverageRate, secondProduct.getAverageRate(), 0.001);
        Assert.assertEquals(Arrays.asList(firstRate, secondRate), secondProduct.getRates());
    }
    @Test
    public void requestCheck(){
        Assert.assertEquals(StateType.PROCESSING, firstRequest.getStateOfRequest());
        Assert.assertEquals(RequestType.REGISTER_SELLER, firstRequest.getTypeOfRequest());
        firstRequest.acceptRequest();
        Assert.assertEquals(StateType.ACCEPTED, firstRequest.getStateOfRequest());
        firstRequest.declineRequest();
        Assert.assertEquals(StateType.DECLINED, firstRequest.getStateOfRequest());

    }
}
