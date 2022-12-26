package ru.sbt.store.core.metamodels;

import ru.sbt.store.core.entities.Info;
import ru.sbt.store.core.entities.Language;
import ru.sbt.store.core.entities.Product;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Info.class)
public class Info_ {
    public static volatile SingularAttribute<Info, Long> id;
    public static volatile SingularAttribute<Info, String> title;
    public static volatile SingularAttribute<Info, String> description;
    public static volatile SingularAttribute<Info, Language> language;
    public static volatile SingularAttribute<Info, Product> product;
}
