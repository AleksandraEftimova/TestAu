package webp.testau;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import webp.testau.model.Category;
import webp.testau.model.Manufacturer;
import webp.testau.model.Product;
import webp.testau.model.Role;
import webp.testau.service.CategoryService;
import webp.testau.service.ManufacturerService;
import webp.testau.service.ProductService;
import webp.testau.service.UserService;

@ActiveProfiles("test")
//avtomatski vo paketot se bara klasa so SpringBootApplication i ke ja iskoristi za testovite
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TestAuApplicationTests {

    //ke testirame crud funckionalnostite od ProductController
    MockMvc mockMvc;


    @Autowired
    UserService userService;

    @Autowired
    ManufacturerService manufacturerService;

    @Autowired
    CategoryService categoryService;


    @Autowired
    ProductService productService;

    //staticki promenlivi
    private static Category c1;
    private static Manufacturer m1;
    private static boolean dataInitialized = false;

    //se povikuva pred sekoj test
    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        initData();
    }

    //za inicijalizacija na podatoci
    private void initData() {
        if (!dataInitialized) {
            c1 = categoryService.create("c1", "c1");
            categoryService.create("c2", "c2");

            m1 = manufacturerService.save("m1", "m1").get();
            manufacturerService.save("m2", "m2");

            String user = "user";
            String admin = "admin";

            userService.register(user, user, user, user, user, Role.ROLE_USER);
            userService.register(admin, admin, admin, admin, admin, Role.ROLE_ADMIN);
            dataInitialized = true;
        }
    }


    //dali aplikacijata e uspesno startuvana
    @Test
    void contextLoads() {
    }


    //dali vrakjame produkti
    @Test
    public void testGetProducts() throws Exception {
        //se zema se za produktite
        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.get("/products");
        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.model().attribute("bodyContent", "products"))
                .andExpect(MockMvcResultMatchers.view().name("master-template"));

    }

    @Test
    public void testDeleteProduct() throws Exception {
        Product product = this.productService.save("test", 2.0, 2, c1.getId(), m1.getId()).get();
        MockHttpServletRequestBuilder productDeleteRequest = MockMvcRequestBuilders
                .delete("/products/delete/" + product.getId());

        this.mockMvc.perform(productDeleteRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products"));
    }

}
