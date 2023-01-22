package webp.testau.service.implementations;

import org.springframework.stereotype.Service;
import webp.testau.model.Product;
import webp.testau.model.ShoppingCart;
import webp.testau.model.User;
import webp.testau.model.enumerations.ShoppingCartStatus;
import webp.testau.model.exceptions.ProductAlreadyInShoppingCartException;
import webp.testau.model.exceptions.ProductNotFoundException;
import webp.testau.model.exceptions.ShoppingCartNotFoundException;
import webp.testau.model.exceptions.UserNotFoundException;
import webp.testau.repository.InMemoryProductRepository;
import webp.testau.repository.InMemoryShoppingCartRepository;
import webp.testau.repository.InMemoryUserRepository;
import webp.testau.service.ShoppingCartService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    //injections
    private final InMemoryShoppingCartRepository shoppingCartRepository;
    private final InMemoryUserRepository userRepository;
    private final InMemoryProductRepository productRepository;

    public ShoppingCartServiceImpl(InMemoryShoppingCartRepository shoppingCartRepository, InMemoryUserRepository userRepository, InMemoryProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> listAllProductsInShoppingCart(Long cartId) {
        //ako ne postoi kosnickata frli exception
        if(!this.shoppingCartRepository.findById(cartId).isEmpty()) {
            throw new ShoppingCartNotFoundException(cartId);
        }
        return this.shoppingCartRepository.findById(cartId).get().getProducts();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        //ako nemam aktivna Created kosnicka togas kreirame nova
        return this.shoppingCartRepository
                .findByUsernameAndStatus(username, ShoppingCartStatus.CREATED)
                .orElseGet( () -> {
                    User user = this.userRepository.findByUsername(username)
                            .orElseThrow( () -> new UserNotFoundException(username));
                    ShoppingCart shoppingCart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        //pronaogjanje na produktot sto sakame da go dodademe
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        //dali produktot postoi vo kosnickata, ima poveke od 0 so isto id
        if(shoppingCart.getProducts()
                .stream().filter(r->r.getId().equals(productId))
                .collect(Collectors.toList()).size() > 0) {
            throw new ProductAlreadyInShoppingCartException(productId, username);
        }
        //go dodavame produktot i zacuvuvame
        shoppingCart.getProducts().add(product);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
