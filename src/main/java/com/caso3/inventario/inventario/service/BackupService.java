package com.caso3.inventario.inventario.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class BackupService {
    private final String DB_NAME = "inventario";
    private final String DB_USER = "root";

    private final String MYSQL_DUMP_PATH =
        "C:\\xampp\\mysql\\bin\\mysqldump.exe";

    private final String BACKUP_PATH =
        "C:\\backups\\";
    @Scheduled(cron = "0 0 2 * * SUN")
    public void realizarBackup() {
    try {

        File directorio = new File(BACKUP_PATH);

        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        String fecha = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
                .format(new Date());

        String backupFile =
                BACKUP_PATH + "backup_" + fecha + ".sql";

        ProcessBuilder processBuilder = new ProcessBuilder(
                MYSQL_DUMP_PATH,
                "-u",
                DB_USER,
                DB_NAME,
                "-r",
                backupFile
        );

        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        int resultado = process.waitFor();

        if (resultado == 0) {
            System.out.println("Backup creado correctamente");
        } else {
            System.out.println("Error al crear backup");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}}
