package ShopAppBackend.Enums;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.persistence.Enumerated;

public enum SortOption  {
    Alphabetically,Ascending_price,Descending_price;

    public Sort test(SortOption sortOption){

        switch (sortOption)
        {
            case Alphabetically:return  Sort.by("product_Name").ascending();
            case Ascending_price:return  Sort.by("product_Price").ascending();
            case Descending_price:return  Sort.by("product_Price").descending();

        }
//        return Sort.unsorted();
        return null;
    }


}
