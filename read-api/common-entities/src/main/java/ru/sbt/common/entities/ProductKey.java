package ru.sbt.common.entities;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProductKey {

    private Long productId;
    private Long languageId;
}
