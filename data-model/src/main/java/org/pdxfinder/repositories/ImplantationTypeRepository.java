package org.pdxfinder.repositories;

import org.pdxfinder.dao.ImplantationType;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for implantation site records
 */
@Repository
public interface ImplantationTypeRepository extends Neo4jRepository<ImplantationType, Long> {

    @Query("MATCH (t:ImplantationType) WHERE t.name = {0} RETURN t")
    ImplantationType findByName(String name);

}
