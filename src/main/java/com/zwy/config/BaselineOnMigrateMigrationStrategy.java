package com.zwy.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.stereotype.Component;

/**
 * 类功能描述:　数据库初始化flyway
 */
@Component
public class BaselineOnMigrateMigrationStrategy implements FlywayMigrationStrategy {
    @Override
    public void migrate(Flyway flyway) {
        FluentConfiguration configure = Flyway.configure();
        configure.baselineOnMigrate(true);
        flyway.migrate();
    }
}
