package com.eticket.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import static com.eticket.configurations.constants.ConstantsConfiguration.OFFICIAL_ENTITY_TABLE_NAME;

@Entity
@Table(name = OFFICIAL_ENTITY_TABLE_NAME)
@Getter
@Setter
public class OfficialEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long officialId;
    private String officialName;
    @Column(unique = true)
    private String officialCode;
    private LocalDateTime birthDate;

    @OneToMany(mappedBy = "officialEntity",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<TicketDetailsEntity> ticketDetailsEntity;

}
