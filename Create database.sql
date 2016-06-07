SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `User`, `Producer`, `Filter`, `Album`, `PhotographerCustomer`, `Order`, `Product`, `PhotographerProduct`, `ProductCopy`, `Photo`, `Customer`, `Photographer`, `AlbumPhoto`;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `User` (
	`UserID`		INT(10) 			NOT NULL	AUTO_INCREMENT,		-- Primary Key
	`Name`			VARCHAR(50)	 		NOT NULL,
	`Username`		VARCHAR(50),
	`Password`		VARCHAR(50),

	UNIQUE(`Username`),
	PRIMARY KEY (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `Producer` (
	`ProducerID`		INT(10)		NOT NULL,		-- Primary Foreign key

	PRIMARY KEY (`ProducerID`),
	`CompanyName`	VARCHAR(50)		NOT NULL,	
	CONSTRAINT `Producer_ibfk_1` 	FOREIGN KEY (`ProducerID`) REFERENCES `User` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Photographer` (
	`PhotographerID`	INT(10)		NOT NULL,	-- Primary Foreign key 
	`ProducerID`		INT(10)		NOT NULL,	-- Foreign key

	PRIMARY KEY (`PhotographerID`),
	CONSTRAINT `Photographer_ibfk_1` FOREIGN KEY (`PhotographerID`) REFERENCES `User` (`UserID`),
	CONSTRAINT `Photographer_ibfk_2` FOREIGN KEY (`ProducerID`) REFERENCES `Producer` (`ProducerID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Customer` (
	`CustomerID`		INT(10)		NOT NULL, 	-- Primary Foreign key 

	PRIMARY KEY (`CustomerID`),
	CONSTRAINT `Customer_ibfk_1` 	FOREIGN KEY (`CustomerID`) REFERENCES `User` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Filter` (
	`FilterID`		INT(10)			NOT NULL	AUTO_INCREMENT,	-- Primary key
	`Name`			VARCHAR(50)		NOT NULL,

	PRIMARY KEY (`FilterID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Photo` (
	`PhotoID` 		INT(10)			NOT NULL	AUTO_INCREMENT,		-- Primary key
	`FilePath`		MEDIUMTEXT		NOT NULL,

	PRIMARY KEY (`PhotoID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Album` (
	`AlbumID`					INT(10) 	NOT NULL AUTO_INCREMENT,	-- Primary key
	`PhotographerID`			INT(10)		NOT NULL,	-- Foreign key
	`CustomerID`				INT(10)		NOT NULL,	-- Foreign key
	`AlbumName`					VARCHAR(30) NOT NULL,

	PRIMARY KEY (`AlbumID`),
	CONSTRAINT `PhotographerCustomer_ibfk_1` FOREIGN KEY (`PhotographerID`) REFERENCES `Photographer` (`PhotographerID`),
	CONSTRAINT `PhotographerCustomer_ibfk_2` FOREIGN KEY (`CustomerID`) REFERENCES `Customer` (`CustomerID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `AlbumPhoto` (
	`AlbumID`					INT(10)			NOT NULL, 	-- Foreign key
	`PhotoID`					INT(10)			NOT NULL,	-- Foreign key

	CONSTRAINT `AlbumPhoto_ibfk_1` FOREIGN KEY (`AlbumID`) REFERENCES `Album` (`AlbumID`),
	CONSTRAINT `AlbumPhoto_ibfk_2` FOREIGN KEY (`PhotoID`) REFERENCES `Photo` (`PhotoID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Product` (
	`ProductID`					INT(10)			NOT NULL	AUTO_INCREMENT,	-- Primary key
	`ProducerID`				INT(10)			NOT NULL,	-- Foreign key
	`Name`						VARCHAR(20)		NOT NULL,
	`Description`				MEDIUMTEXT		NOT NULL,
	`MinimumPrice`				DECIMAL(5, 2)	NOT NULL,

	PRIMARY KEY (`ProductID`),
	CONSTRAINT `Product_ibfk_1` FOREIGN KEY (`ProducerID`) REFERENCES `Producer` (`ProducerID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `PhotographerProduct` (
	`PhotographerProductID`		INT(10)			NOT NULL	AUTO_INCREMENT,	-- Primary key
	`ProductID`					INT(10)			NOT NULL,
	`PhotographerID`			INT(10)			NOT NULL,	-- Foreign key
	`ProfitPrice`				DECIMAL(5, 2)	NOT NULL,

	PRIMARY KEY (`PhotographerProductID`),
	CONSTRAINT `PhotographerProduct_ibfk_1` FOREIGN KEY (`ProductID`) REFERENCES `Product` (`ProductID`),
	CONSTRAINT `PhotographerProduct_ibfk_2` FOREIGN KEY (`PhotographerID`) REFERENCES `Photographer` (`PhotographerID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Order` (
	`OrderID`					INT(10)			NOT NULL	AUTO_INCREMENT,	-- Primary key
	`CustomerID`				INT(10)			NOT NULL,	-- Foreign key
	`ProducerID`				INT(10)			NOT NULL,	-- Foreign key
	`PhotographerID`			INT(10)			NOT NULL,	-- Foreign key
	`Payed`						INT(10)			NOT NULL,	
	
	PRIMARY KEY (`OrderID`),
	CONSTRAINT `Order_ibfk_1` FOREIGN KEY (`CustomerID`) REFERENCES `Customer` (`CustomerID`),
	CONSTRAINT `Order_ibfk_2` FOREIGN KEY (`ProducerID`) REFERENCES `Producer` (`ProducerID`),
	CONSTRAINT `Order_ibfk_3` FOREIGN KEY (`PhotographerID`) REFERENCES `Photographer` (`PhotographerID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `ProductCopy` (
	`PhotographerProductID`		INT(10)			NOT NULL,	-- Foreign key
	`PhotoID`					INT(10)			NOT NULL,	-- Foreign key
	`FilterID`					INT(10)			NOT NULL,	-- Foreign key
	`OrderID`					INT(10)			NOT NULL,	-- Foreign key

	CONSTRAINT `ProductCopy_ibfk_1` FOREIGN KEY (`PhotographerProductID`) REFERENCES `PhotographerProduct` (`PhotographerProductID`),
	CONSTRAINT `ProductCopy_ibfk_2` FOREIGN KEY (`PhotoID`) REFERENCES `Photo` (`PhotoID`),
	CONSTRAINT `ProductCopy_ibfk_3` FOREIGN KEY (`FilterID`) REFERENCES `Filter` (`FilterID`),
	CONSTRAINT `ProductCopy_ibfk_4` FOREIGN KEY (`OrderID`) REFERENCES `Order` (`OrderID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `CustomerAlbum` (
	`CustomerID`				INT(10)			NOT NULL,	-- Foreign key
	`AlbumID`					INT(10)			NOT NULL,	-- Foreign key

	PRIMARY KEY (`CustomerID`, `AlbumID`),
	CONSTRAINT `CustomerAlbum_ibfk_1` FOREIGN KEY (`CustomerID`) REFERENCES `Customer` (`CustomerID`),
	CONSTRAINT `CustomerAlbum_ibfk_2` FOREIGN KEY (`AlbumID`) REFERENCES `Album` (`AlbumID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Triggers
DELIMITER //

CREATE TRIGGER checkFilterName
     BEFORE INSERT ON Filter FOR EACH ROW
     BEGIN
          IF NEW.Name NOT IN ('Sepia', 'Black and White', 'Color')
          THEN
               SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Cannot insert, values unknown';
          END IF;
     END//