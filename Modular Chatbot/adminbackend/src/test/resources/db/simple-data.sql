-- MySQL dump 10.13  Distrib 5.7.29, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: frankenbot
-- ------------------------------------------------------
-- Server version	5.7.29-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `auth_token`
--

DROP TABLE IF EXISTS `auth_token`;
CREATE TABLE `auth_token` (
  `token` varchar(255) NOT NULL,
  `creation_date` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`token`),
  KEY `FKcitnjcm1jtmvivddmj7cpvoue` (`created_by`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_token`
--

LOCK TABLES `auth_token` WRITE;
/*!40000 ALTER TABLE `auth_token` DISABLE KEYS */;
INSERT INTO `auth_token` VALUES ('18e9d995-dd5f-49d9-8262-4b16a6f0c020','2020-04-21 14:52:52',1);
/*!40000 ALTER TABLE `auth_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatbot`
--

DROP TABLE IF EXISTS `chatbot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chatbot` (
  `id` bigint(20) NOT NULL,
  `fallback_message` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `welcome_message` varchar(255) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjy5kl08agtgqm939nuga29p6k` (`created_by`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatbot`
--

LOCK TABLES `chatbot` WRITE;
/*!40000 ALTER TABLE `chatbot` DISABLE KEYS */;
INSERT INTO `chatbot` VALUES (2,'I cannot understand you. Can you please speak a bit louder?','Mein Chatbot','Hallo ich bin der Chatbot?',1);
/*!40000 ALTER TABLE `chatbot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatbot_modules`
--

DROP TABLE IF EXISTS `chatbot_modules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chatbot_modules` (
  `chatbot_id` bigint(20) NOT NULL,
  `modules_id` bigint(20) NOT NULL,
  KEY `FKp1gtr12bggwq4dmm8ersvhw6m` (`modules_id`),
  KEY `FKr13yucke9fej8wx9sjuk6mefw` (`chatbot_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatbot_modules`
--

LOCK TABLES `chatbot_modules` WRITE;
/*!40000 ALTER TABLE `chatbot_modules` DISABLE KEYS */;
INSERT INTO `chatbot_modules` VALUES (2,3);
/*!40000 ALTER TABLE `chatbot_modules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `default_module`
--

DROP TABLE IF EXISTS `default_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `default_module` (
  `id` bigint(20) NOT NULL,
  `language` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmla91c54apcm5bmwvx1lu0tue` (`created_by`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `default_module`
--

LOCK TABLES `default_module` WRITE;
/*!40000 ALTER TABLE `default_module` DISABLE KEYS */;
INSERT INTO `default_module` VALUES (3,NULL,'Mein Testmodul',1);
/*!40000 ALTER TABLE `default_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dialog_node`
--

DROP TABLE IF EXISTS `dialog_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dialog_node` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `ordering` int(11) DEFAULT NULL,
  `default_module` bigint(20) DEFAULT NULL,
  `parent` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjkbuxgn02nvw9nfwat5n1cmxx` (`default_module`),
  KEY `FKqua50l0uujmxotd79t04aow8s` (`parent`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dialog_node`
--

LOCK TABLES `dialog_node` WRITE;
/*!40000 ALTER TABLE `dialog_node` DISABLE KEYS */;
INSERT INTO `dialog_node` VALUES (12,'Greeting',0,3,NULL),(13,'Tschuess',1,3,NULL),(14,'Child of greeting',0,3,12);
/*!40000 ALTER TABLE `dialog_node` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dialog_node_intent_condition`
--

DROP TABLE IF EXISTS `dialog_node_intent_condition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dialog_node_intent_condition` (
  `id` bigint(20) NOT NULL,
  `intent` bigint(20) DEFAULT NULL,
  `node` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9tlod2vxeebch0ort6go9ic4t` (`intent`),
  KEY `FKt8fxm3iy3ot6fh8u5qrxbd0qm` (`node`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dialog_node_intent_condition`
--

LOCK TABLES `dialog_node_intent_condition` WRITE;
/*!40000 ALTER TABLE `dialog_node_intent_condition` DISABLE KEYS */;
INSERT INTO `dialog_node_intent_condition` VALUES (15,4,12);
/*!40000 ALTER TABLE `dialog_node_intent_condition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dialog_node_text_answer`
--

DROP TABLE IF EXISTS `dialog_node_text_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dialog_node_text_answer` (
  `id` bigint(20) NOT NULL,
  `ordering` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `node` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt229n08f22ushnqwb7hwcayr3` (`node`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dialog_node_text_answer`
--

LOCK TABLES `dialog_node_text_answer` WRITE;
/*!40000 ALTER TABLE `dialog_node_text_answer` DISABLE KEYS */;
INSERT INTO `dialog_node_text_answer` VALUES (16,0,'Hallo, schön dich kennenzulernen!',12);
/*!40000 ALTER TABLE `dialog_node_text_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entity`
--

DROP TABLE IF EXISTS `entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entity` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `module` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf80xvhfx2udd5q8q8k9gy7jc9` (`created_by`),
  KEY `FKe2tnbrm2i5hshy028bx7d7h27` (`module`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entity`
--

LOCK TABLES `entity` WRITE;
/*!40000 ALTER TABLE `entity` DISABLE KEYS */;
INSERT INTO `entity` VALUES (7,'Gebäck',1,3);
/*!40000 ALTER TABLE `entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entity_annotation`
--

DROP TABLE IF EXISTS `entity_annotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entity_annotation` (
  `id` bigint(20) NOT NULL,
  `begin_index` int(11) DEFAULT NULL,
  `end_index` int(11) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `entity_value` bigint(20) DEFAULT NULL,
  `utterance` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnbytcep60t1as3n8043d58j35` (`created_by`),
  KEY `FK8ofv1vf4ka3r07b37ggj5tun2` (`entity_value`),
  KEY `FKmnhg6fynev84rmjblrd0fiuyp` (`utterance`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entity_annotation`
--

LOCK TABLES `entity_annotation` WRITE;
/*!40000 ALTER TABLE `entity_annotation` DISABLE KEYS */;
INSERT INTO `entity_annotation` VALUES (11,10,21,1,9,NULL);
/*!40000 ALTER TABLE `entity_annotation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entity_synonym`
--

DROP TABLE IF EXISTS `entity_synonym`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entity_synonym` (
  `id` bigint(20) NOT NULL,
  `synonym` varchar(255) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `entity_value` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5uucftxsm2k6j913h0774u830` (`created_by`),
  KEY `FKjmoeqlsvjnseqm6ugb6ffx0i6` (`entity_value`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entity_synonym`
--

LOCK TABLES `entity_synonym` WRITE;
/*!40000 ALTER TABLE `entity_synonym` DISABLE KEYS */;
INSERT INTO `entity_synonym` VALUES (10,'Erdbeertarte',1,9);
/*!40000 ALTER TABLE `entity_synonym` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entity_value`
--

DROP TABLE IF EXISTS `entity_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entity_value` (
  `id` bigint(20) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `entity` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgichnay19emovdylrkc4v1exh` (`created_by`),
  KEY `FKoo9g9yw40trfi3i0v4n9094ft` (`entity`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entity_value`
--

LOCK TABLES `entity_value` WRITE;
/*!40000 ALTER TABLE `entity_value` DISABLE KEYS */;
INSERT INTO `entity_value` VALUES (8,'Apfelkuchen',1,7),(9,'Erdbeertorte',1,7);
/*!40000 ALTER TABLE `entity_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (17),(17),(17),(17),(17),(17),(17),(17),(17),(17),(17),(17);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `intent`
--

DROP TABLE IF EXISTS `intent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `intent` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `module` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb0tnulvtap1rofa309ml40bfm` (`created_by`),
  KEY `FK7gus7ckd7oi1k8k3eflgf95fi` (`module`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `intent`
--

LOCK TABLES `intent` WRITE;
/*!40000 ALTER TABLE `intent` DISABLE KEYS */;
INSERT INTO `intent` VALUES (4,'Begruessungen und andere Anreden','Begruessung',1,3);
/*!40000 ALTER TABLE `intent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'test@test.de','klaus','5201665ddd71fece58694f596e93383d9a5023c37f8ff18332957af05461f37ca6fbdf2d034fad9deba5511135ada179a5398b712dc3a1c00e567be7a5179127','0xeERVpI0EryTUZXe+k5YQ==');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utterance`
--

DROP TABLE IF EXISTS `utterance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utterance` (
  `id` bigint(20) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `intent` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlftgwiw7hq15bhih3m7wb6l1t` (`created_by`),
  KEY `FK57qa5r7n2gfm1a4k7pthta6ud` (`intent`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utterance`
--

LOCK TABLES `utterance` WRITE;
/*!40000 ALTER TABLE `utterance` DISABLE KEYS */;
INSERT INTO `utterance` VALUES (5,'Guten morgen',1,4),(6,'Moin moin Apfelkuchen',1,4);
/*!40000 ALTER TABLE `utterance` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-21 16:54:02
