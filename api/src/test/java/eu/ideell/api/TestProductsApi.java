package eu.ideell.api;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;

import org.junit.Assert;
import org.junit.Test;

import com.googlecode.objectify.Key;

import eu.ideell.api.jersey.TestJersey;
import eu.ideell.api.mongodb.entity.Customer;
import eu.ideell.api.mongodb.entity.Department;
import eu.ideell.api.mongodb.entity.Product;
import eu.ideell.api.service.model.ProductRequest;
import eu.ideell.api.service.model.StoreSearchRequest;
import eu.ideell.api.service.model.StoreSearchResponse;
import eu.ideell.api.type.ProductCategory;

public class TestProductsApi extends TestJersey {

  @Test
  public void test_postUnauthorized() {
    // Given
    final ProductRequest body = new ProductRequest();

    // When
    try {
      super.postUnauthenticated("products", body, Void.class);
      Assert.fail();
    } catch (final ForbiddenException e) {
      // Ok
    }
  }

  @Test
  public void test_postAuthorizedWithNulls() {
    // Given
    final ProductRequest body = new ProductRequest();

    // When
    try {
      super.postAuthenticated("products", body, Void.class);
      Assert.fail();
    } catch (final BadRequestException e) {
      // Ok
    }
  }

  @Test
  public void test_postAuthorizedWithModel() {
    // Given
    final ProductRequest model = new ProductRequest("product name", "description", ProductCategory.cosmetic);
    final StoreSearchRequest search = new StoreSearchRequest("");

    // When
    final long id = super.postAuthenticated("products", model, Long.class);
    final StoreSearchResponse searchResult = super.postUnauthenticated("public/search", search, StoreSearchResponse.class);

    // Then
    final Key<Customer> customer = Key.create(Customer.class, "default");
    final Key<Department> department = Key.create(customer, Department.class, "default");
    final Key<Product> key = Key.create(department, Product.class, id);
    final Product result = testLoad(key);

    Assert.assertNotNull(result);
    Assert.assertEquals(model.getName(), result.getName());
    Assert.assertEquals(model.getDescription(), result.getDescription().getValue());
    Assert.assertEquals(model.getCategory(), result.getCategory());

    Assert.assertFalse(searchResult.getProducts().isEmpty());
    Assert.assertEquals(model.getName(), searchResult.getProducts().get(0).getName());
    Assert.assertEquals(model.getDescription(), searchResult.getProducts().get(0).getDescription());
    Assert.assertEquals(model.getCategory(), searchResult.getProducts().get(0).getCategory());
  }
}