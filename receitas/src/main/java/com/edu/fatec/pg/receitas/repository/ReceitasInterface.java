package com.edu.fatec.pg.receitas.repository;

import com.edu.fatec.pg.receitas.model.ReceitasModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitasInterface extends JpaRepository<ReceitasModel, Integer> {
}
