package com.zenjava.firstcontact.service.repository;

import com.zenjava.firstcontact.service.Contact;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class ContactSpecifications
{
    public static Specification<Contact> searchContacts(final String[] keywords)
    {
        return new Specification<Contact>()
        {
            public Predicate toPredicate(Root contact, CriteriaQuery query, CriteriaBuilder builder)
            {
                Predicate predicate = builder.conjunction();

                if (keywords != null)
                {
                    for (String keyword : keywords)
                    {
                        if (keyword != null && keyword.length() > 0)
                        {
                            String matchTerm = "%" + keyword.toLowerCase() + "%";
                            predicate.getExpressions().add(builder.or(
                                    builder.like(builder.lower(contact.get("firstName")), matchTerm),
                                    builder.like(builder.lower(contact.get("lastName")), matchTerm)
                            ));
                        }
                    }
                }

                return predicate;
            }
        };
    }
}
