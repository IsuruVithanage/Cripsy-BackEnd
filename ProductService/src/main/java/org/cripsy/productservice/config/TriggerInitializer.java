package org.cripsy.productservice.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class TriggerInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @PostConstruct
    private void update_product_ratings(){
        /*
            This will update overall rating details
            on product table when adding new review
        */

        String updateRatingDetailsSQL = """
            CREATE OR REPLACE FUNCTION update_product_ratings()
            RETURNS TRIGGER AS $$
            DECLARE
                rating_stats RECORD;
            BEGIN
                SELECT COUNT(*) AS total_count, AVG(rating) AS average_rating
                INTO rating_stats
                FROM ratings
                WHERE product_id = NEW.product_id;
            
                UPDATE product
                SET
                    rating_count = rating_stats.total_count,
                    avg_ratings = rating_stats.average_rating
                WHERE product_id = NEW.product_id;
            
                RETURN NULL;
            END;
            $$ LANGUAGE plpgsql;
            
            CREATE OR REPLACE TRIGGER before_rating_insert
            AFTER INSERT ON ratings
            FOR EACH ROW
            EXECUTE FUNCTION update_product_ratings();
        """;


        try {
            jdbcTemplate.execute(updateRatingDetailsSQL);
        } catch (Exception e) {
            System.out.println("Failed to create updateRatingDetails trigger: " + e.getMessage());
        }
    }

    @PostConstruct
    private void createReserveItemsProcedure(){
        /*
            This PROCEDURE will insert bulk Products with Reserved stocks
        */

        String reserveItemsSQL = """
            CREATE OR REPLACE PROCEDURE insertReservedStocks(
                valuesToAdd TEXT
            ) LANGUAGE plpgsql AS $$
            BEGIN
                EXECUTE format('INSERT INTO reserved_stock (transaction_id, product_id, quantity) VALUES %s', valuesToAdd);
            END;
            $$;
        """;

        try {
            jdbcTemplate.execute(reserveItemsSQL);
        } catch (Exception e) {
            System.out.println("Failed to create insertReservedStocks function: " + e.getMessage());
        }
    }
}

