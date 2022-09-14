package com.sdacademy.book_shop.entities.user;

import com.sdacademy.book_shop.entities.cartNoder.OrderCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String city;
    private String street;
    private int streetNumber;
    private String block;
    private String ap;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name ="user_addresses",
            joinColumns = @JoinColumn(name="address_id"),
            inverseJoinColumns = @JoinColumn(name="user_id") )
    private User user;

    @OneToMany(mappedBy = "address")
    private List<OrderCommand> ordersList;

}
