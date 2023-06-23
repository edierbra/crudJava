-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: videogame_db
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `id_categoria_pk` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id_categoria_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Acción'),(2,'Aventura'),(3,'Estrategía'),(4,'Deportes'),(5,'RPG'),(6,'Aracade'),(7,'Simulacion'),(8,'Juegos de mesa'),(9,'Juegos musicales'),(10,'Horror'),(11,'Supervivencia'),(12,'Casual'),(13,'Rompecabezas'),(14,'Puzzle'),(15,'Carreras'),(16,'Disparos'),(17,'Lucha'),(18,'Sigilo'),(19,'Juegos de cartas'),(20,'Educacion'),(23,'Interactivosss'),(24,'Interactivas'),(25,'Musica');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plataformas`
--

DROP TABLE IF EXISTS `plataformas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plataformas` (
  `id_plataforma_pk` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id_plataforma_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plataformas`
--

LOCK TABLES `plataformas` WRITE;
/*!40000 ALTER TABLE `plataformas` DISABLE KEYS */;
INSERT INTO `plataformas` VALUES (1,'Xbox 2'),(2,'PlayStation'),(3,'Nintendo Switch'),(4,'PC'),(5,'Mobile'),(6,'Commodore 64'),(7,'Neo Geo'),(8,'PC Engine'),(9,'Atari 2600'),(10,'Super Nintendo Entertainment System'),(11,'Sega Dreamcast'),(12,'PlayStation Portable'),(13,'Nintendo 3DS'),(14,'Nintendo DS'),(15,'Game Boy Advance'),(16,'Game Boy Color'),(17,'Sega Genesis 2'),(18,'Wii U'),(19,'Wii'),(20,'PlayStation Vita'),(21,'Xbox 3'),(22,'Xbox 4');
/*!40000 ALTER TABLE `plataformas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_videojuego`
--

DROP TABLE IF EXISTS `usuario_videojuego`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_videojuego` (
  `id_usuario_fk` int NOT NULL,
  `id_videojuego_fk` int NOT NULL,
  PRIMARY KEY (`id_usuario_fk`,`id_videojuego_fk`),
  KEY `id_videojuego_fk` (`id_videojuego_fk`),
  CONSTRAINT `usuario_videojuego_ibfk_1` FOREIGN KEY (`id_usuario_fk`) REFERENCES `usuarios` (`id_usuario_pk`),
  CONSTRAINT `usuario_videojuego_ibfk_2` FOREIGN KEY (`id_videojuego_fk`) REFERENCES `videojuegos` (`id_videojuego_pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_videojuego`
--

LOCK TABLES `usuario_videojuego` WRITE;
/*!40000 ALTER TABLE `usuario_videojuego` DISABLE KEYS */;
INSERT INTO `usuario_videojuego` VALUES (1,1),(2,1),(1,2),(2,2),(3,3),(3,4),(4,5),(5,6),(5,7),(6,8),(7,9),(7,10),(8,11),(8,12),(9,13),(10,14),(10,15),(11,16),(11,17),(12,18),(13,19),(13,20);
/*!40000 ALTER TABLE `usuario_videojuego` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuario_pk` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `edad` int NOT NULL,
  `email` varchar(100) NOT NULL,
  `clave` varchar(255) NOT NULL,
  `cargo` varchar(1) NOT NULL,
  PRIMARY KEY (`id_usuario_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Edier Dario Bravo Bravo',25,'edierbra@gmail.com','202cb962ac59075b964b07152d234b70','1'),(2,'Juan Pablo Cuervo',30,'juanpablo@gmail.com','202cb962ac59075b964b07152d234b70','1'),(3,'Alice Smith',28,'alicesmith@gmail.com','202cb962ac59075b964b07152d234b70','1'),(4,'Bob Johnson',35,'bobjohnson@gmail.com','202cb962ac59075b964b07152d234b70','1'),(5,'Emily Davis',22,'emilydavis@gmail.com','202cb962ac59075b964b07152d234b70','1'),(6,'Michael Wilson',29,'michaelwilson@gmail.com','202cb962ac59075b964b07152d234b70','1'),(7,'Jessica Anderson',31,'jessicaanderson@gmail.com','202cb962ac59075b964b07152d234b70','1'),(8,'David Thompson',26,'davidthompson@gmail.com','202cb962ac59075b964b07152d234b70','1'),(9,'Sarah Brown',33,'sarahbrown@gmail.com','202cb962ac59075b964b07152d234b70','1'),(10,'Christopher Martinez',27,'christophermartinez@gmail.com','202cb962ac59075b964b07152d234b70','1'),(11,'Olivia Taylor',24,'oliviataylor@gmail.com','202cb962ac59075b964b07152d234b70','1'),(12,'Daniel Davis',32,'danieldavis@gmail.com','202cb962ac59075b964b07152d234b70','1'),(13,'Sophia Harris',29,'sophiaharris@gmail.com','202cb962ac59075b964b07152d234b70','1'),(14,'Matthew Clark',26,'matthewclark@gmail.com','202cb962ac59075b964b07152d234b70','1'),(15,'Ava Young',31,'avayoung@gmail.com','202cb962ac59075b964b07152d234b70','1'),(16,'James Lee',28,'jameslee@gmail.com','202cb962ac59075b964b07152d234b70','1'),(17,'Emma Lewis',25,'emmalewis@gmail.com','202cb962ac59075b964b07152d234b70','1'),(18,'Alexander Turner',34,'alexanderturner@gmail.com','202cb962ac59075b964b07152d234b70','1'),(19,'Mia Walker',27,'miawalker@gmail.com','202cb962ac59075b964b07152d234b70','1'),(20,'William Baker',30,'williambaker@gmail.com','202cb962ac59075b964b07152d234b70','1'),(38,'Sebastian',34,'sebastian@gmail.com','37693cfc748049e45d87b8c7d8b9aacd','1'),(39,'edier',23,'camila@gmail.com','202cb962ac59075b964b07152d234b70','1'),(40,'Paula Campo',34,'paula@gmail.com','202cb962ac59075b964b07152d234b70','1');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `videojuegos`
--

DROP TABLE IF EXISTS `videojuegos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `videojuegos` (
  `id_videojuego_pk` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(255) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `tamano` decimal(10,2) NOT NULL,
  `id_plataforma_fk` int DEFAULT NULL,
  `id_categoria_fk` int DEFAULT NULL,
  PRIMARY KEY (`id_videojuego_pk`),
  KEY `id_plataforma_fk` (`id_plataforma_fk`),
  KEY `id_categoria_fk` (`id_categoria_fk`),
  CONSTRAINT `videojuegos_ibfk_1` FOREIGN KEY (`id_plataforma_fk`) REFERENCES `plataformas` (`id_plataforma_pk`),
  CONSTRAINT `videojuegos_ibfk_2` FOREIGN KEY (`id_categoria_fk`) REFERENCES `categorias` (`id_categoria_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `videojuegos`
--

LOCK TABLES `videojuegos` WRITE;
/*!40000 ALTER TABLE `videojuegos` DISABLE KEYS */;
INSERT INTO `videojuegos` VALUES (1,'Halo Infinite',59.99,50.00,1,1),(2,'God of War',49.99,70.00,2,2),(3,'Super Mario Odyssey',49.99,55.00,3,2),(4,'The Witcher 3: Wild Hunt',39.99,80.00,4,5),(5,'FIFA 22',59.99,45.00,5,4),(6,'Red Dead Redemption 2',59.99,100.00,2,1),(7,'Minecraft',29.99,200.00,3,3),(8,'Call of Duty: Warzone',0.00,120.00,1,1),(9,'Animal Crossing: New Horizons',59.99,60.00,3,2),(10,'Assassin\'s Creed Valhalla',59.99,90.00,4,5),(11,'Fortnite',0.00,30.00,5,1),(12,'Resident Evil Village',59.99,45.00,2,1),(13,'League of Legends',0.00,25.00,4,3),(14,'Grand Theft Auto V',29.99,80.00,1,1),(15,'The Legend of Zelda: Breath of the Wild',59.99,70.00,3,2),(16,'Overwatch',39.99,50.00,4,1),(17,'NBA 2K22',59.99,95.00,5,4),(18,'Cyberpunk 2077',49.99,80.00,2,5),(19,'Pokémon Sword',59.99,55.00,3,2),(20,'Among Us',4.99,0.30,4,3),(66,'Comer',4000.00,333.00,20,16);
/*!40000 ALTER TABLE `videojuegos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-14 21:59:06
