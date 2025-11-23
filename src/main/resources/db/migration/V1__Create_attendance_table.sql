CREATE TABLE attendances (
    -- Chave Primária
    -- @Id e @GeneratedValue(strategy = GenerationType.UUID)
    id UUID NOT NULL,

    -- Chaves Estrangeiras (Mapeamento de Relacionamentos)
    -- @Column(name = "student_id", nullable = false)
    student_id UUID NOT NULL,

    -- @Column(name = "class_id", nullable = false)
    class_id UUID NOT NULL,

    -- Outras Colunas
    -- @Column(nullable = false) (Mapeia java.time.LocalDate para DATE)
    date DATE NOT NULL,

    -- @Enumerated(EnumType.STRING) e @Column(nullable = false)
    -- O enum é armazenado como uma String/VARCHAR.
    status VARCHAR(50) NOT NULL,

    -- Restrições Primárias e de Chave Estrangeira
    CONSTRAINT pk_attendances PRIMARY KEY (id),

    -- Restrição UNIQUE opcional: Garante apenas uma presença/status por aluno por dia.
    -- Se necessário, remova essa linha.
    CONSTRAINT uk_attendances_student_date UNIQUE (student_id, date)

    -- As chaves estrangeiras (Foreign Keys) são tipicamente adicionadas
    -- mas requerem que as tabelas 'students' e 'classes' existam primeiro.
    -- Descomente as linhas abaixo se as tabelas estiverem definidas em migrações anteriores:

    -- CONSTRAINT fk_attendances_student FOREIGN KEY (student_id) REFERENCES students(id),
    -- CONSTRAINT fk_attendances_class FOREIGN KEY (class_id) REFERENCES classes(id)
);