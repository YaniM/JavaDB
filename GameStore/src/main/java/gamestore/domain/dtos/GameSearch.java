package gamestore.domain.dtos;

import java.math.BigDecimal;

public class GameSearch {
    private String title;
    private BigDecimal price;

    public GameSearch(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }

    public GameSearch() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
