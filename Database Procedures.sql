DELIMITER //
DROP PROCEDURE IF EXISTS insertProducer//
create procedure insertProducer(in Name VARCHAR(50), in CompanyName VARCHAR(50), in Username VARCHAR(50), in Password VARCHAR(50))
  BEGIN
    DECLARE lastUserID INT;
    insert into User values (null, name, Username, Password);
    SET lastUserID = (select MAX(UserID) from User);
    insert into Producer values(lastUserID, CompanyName);
END//

DROP PROCEDURE IF EXISTS insertPhotographer//
create procedure insertPhotographer(in Name VARCHAR(50), in ProducerID INT(10), in Username VARCHAR(50), in Password VARCHAR(50))
  BEGIN
    DECLARE lastUserID INT;
    insert into User values (null, name, Username, Password);
    SET lastUserID = (select MAX(UserID) from User);
    insert into Photographer values(lastUserID, ProducerID);
END//

DROP PROCEDURE IF EXISTS insertCustomer//
create procedure insertCustomer(in Name VARCHAR(50))
  BEGIN
    DECLARE lastUserID INT;
    insert into User values (null, name, null, null);
    SET lastUserID = (select MAX(UserID) from User);
    insert into Customer values(lastUserID);
END//

DROP PROCEDURE IF EXISTS getUserFromID//
create procedure getUserFromID(in UserID INT(10))
  COMMENT 'get User from ID.'
  BEGIN
    start transaction;
    Select * from User u where u.UserID = UserID;
    commit;
END//

DROP PROCEDURE IF EXISTS getProducers//
create procedure getProducers()
  COMMENT 'Select all producers.'
  BEGIN
    start transaction;
    Select u.UserID, u.Name, p.ProducerID, p.CompanyName from User u, Producer p where u.UserID = p.ProducerID;
    commit;
END//

DROP PROCEDURE IF EXISTS getPhotographers//
create procedure getPhotographers()
  COMMENT 'Select all photographers.'
  BEGIN
    start transaction;
    Select * from User u, Photographer ph, Producer pr 
      where u.UserID = ph.PhotographerID
      and u.UserID = pr.ProducerID;
    commit;
END//

DROP PROCEDURE IF EXISTS getCustomers//
create procedure getCustomers()
  COMMENT 'Select all customers.'
  BEGIN
    start transaction;
    Select c.CustomerID, u.Name from User u, Customer c where u.UserID = c.CustomerID;
    commit;
END//

DROP PROCEDURE IF EXISTS getUsers//
create procedure getUsers()
  COMMENT 'Select all users.'
  BEGIN
    start transaction;
    Select * from User;
    commit;
END//

DROP PROCEDURE IF EXISTS getOrdersFromProducer//
create procedure getOrdersFromProducer(in ProducerID INT(10))
  COMMENT 'get all orders from a producer.'
  BEGIN
    start transaction;
    Select * from `Order` o where o.ProducerID = ProducerID;
    commit;
END//

DROP PROCEDURE IF EXISTS getProductCopiesFromOrder//
create procedure getProductCopiesFromOrder(in OrderID INT(10))
  COMMENT 'get all product copies from a order.'
  BEGIN
    start transaction;
    Select * from ProductCopy pc where pc.OrderID = OrderID;
    commit;
END//

DROP PROCEDURE IF EXISTS getPhotoFromID//
create procedure getPhotoFromID(in PhotoID INT(10))
  COMMENT 'get photo from ID.'
  BEGIN
    start transaction;
    Select * from Photo p where p.PhotoID = PhotoID;
    commit;
END//

DROP PROCEDURE IF EXISTS getFilterFromID//
create procedure getFilterFromID(in FilterID INT(10))
  COMMENT 'get filter from ID.'
  BEGIN
    start transaction;
    Select * from Filter f where f.FilterID = FilterID;
    commit;
END//

DROP PROCEDURE IF EXISTS getCustomerFromID//
create procedure getCustomerFromID(in CustomerID INT(10))
  COMMENT 'get Customer from ID.'
  BEGIN
    start transaction;
    Select * from User u where u.UserID = CustomerID;
    commit;
END//

DROP PROCEDURE IF EXISTS getPhotographerFromID//
create procedure getPhotographerFromID(in PhotographerID INT(10))
  COMMENT 'get Photographer from ID.'
  BEGIN
    start transaction;
    select p.ProducerID, u.UserID as `PhotographerID`, u.Name as `PhotographerName` from Photographer p, User u where u.UserID = PhotographerID;
    commit;
END//

DROP PROCEDURE IF EXISTS getProducerFromID//
create procedure getProducerFromID(in ProducerID INT(10))
  COMMENT 'get Producer from ID.'
  BEGIN
    start transaction;
    Select u.UserID as `ProducerID`, u.Name as `ProducerName`, p.CompanyName as `CompanyName` from User u, Producer p where u.UserID = ProducerID AND p.ProducerID = ProducerID;
    commit;
END//

DROP PROCEDURE IF EXISTS getPhotosFromAlbum//
create procedure getPhotosFromAlbum(in AlbumID INT(10))
  COMMENT 'get all photos from a album.'
  BEGIN
    start transaction;
    select * from Photo p where p.PhotoID in (select ap.PhotoID from AlbumPhoto ap where ap.AlbumID = AlbumID);
    commit;
END//

DROP PROCEDURE IF EXISTS getOrdersFromProducer//
create procedure getOrdersFromProducer(in ProducerID INT(10))
  COMMENT 'get all orders from a producer.'
  BEGIN
    start transaction;
    select * from `Order` o where o.ProducerID = ProducerID;
    commit;
END//

DROP PROCEDURE IF EXISTS getProductsFromPhotographer//
create procedure getProductsFromPhotographer(in PhotographerID INT(10))
  COMMENT 'get all products from a photographer'
  BEGIN
    start transaction;
    select * from PhotographerProduct pp, Product p where pp.ProductID = p.ProductID AND pp.PhotographerID = PhotographerID;
    commit;
END//

DROP PROCEDURE IF EXISTS getProductsForOrder//
create procedure getProductsForOrder(in OrderIDa INT(10))
  COMMENT 'get all products from a order.'
    BEGIN
    start transaction;
    select p.ProductID, p.ProducerID, p.Name, p.Description, p.MinimumPrice, pc.PhotoID, pc.FilterID, pc.OrderID, pp.PhotographerID, pp.PhotographerProductID from ProductCopy pc, Product p, PhotographerProduct pp, Producer pr, `Order` o
    where pp.ProductID = p.ProductID
      AND pc.PhotographerProductID = pp.PhotographerProductID
      AND pc.OrderID = OrderIDa
      AND o.OrderID = OrderIDa
      AND pr.ProducerID = o.ProducerID;
    commit;
END//

DROP PROCEDURE IF EXISTS getProductsFromProducer//
create procedure getProductsFromProducer(in ProducerID INT(10))
  COMMENT 'get all products from a producer'
  BEGIN
    start transaction;
    select * from Product p where  p.ProducerID = ProducerID;
    commit;
END//

DROP PROCEDURE IF EXISTS getPhotographersFromProducer//
create procedure getPhotographersFromProducer(in ProducerID INT(10))
  COMMENT 'get all photographers from a producer.'
  BEGIN
    start transaction;
    select u.userID as `PhotographerID`, p.ProducerID as `ProducerID`, u.Name as `PhotographerName` 
    from Photographer p, User u 
      where p.PhotographerID = u.UserID 
        and p.ProducerID = 6
      group by u.userID, p.ProducerID, u.Name;
    commit;
END//

DROP PROCEDURE IF EXISTS getUserFromUsername//
create procedure getUserFromUsername(in Username VARCHAR(50))
  COMMENT 'get the user from the username'
  BEGIN
    start transaction;
    select * from User u where u.Username = Username;

    commit;
END//

DROP PROCEDURE IF EXISTS checkLogin//
create procedure checkLogin(IN iUsername VARCHAR(50), IN iPassword VARCHAR(50), OUT oLoginValue INT, OUT oUserID INT)
  BEGIN    
  DECLARE UserID INT;
    start transaction;
    set UserID = (select u.UserID from User u where u.Username = iUsername);
    
    IF EXISTS(select * from User where Username = iUsername AND Password = iPassword)then
    set oLoginValue = 0;
    set oUserID = UserID;
  else 
    set oLoginValue = 1;
    set oUserID = UserID;      
  END IF;
    commit;
END//

DROP PROCEDURE IF EXISTS login//
create procedure login(IN iUsername VARCHAR(50), IN iPassword VARCHAR(50))
  BEGIN    
  CALL login(iUsername, iPassword, @msg, @uid);
  SELECT @msg, @uid;
END//


DROP PROCEDURE IF EXISTS insertPhotoToAlbum//
create procedure insertPhotoToAlbum(in filePath MEDIUMTEXT, in AlbumID INT(10))
  BEGIN
  DECLARE lastPhotoID INT;
  insert into Photo values (null, filePath);
  set lastPhotoID = (select MAX(PhotoID) from Photo);
  insert into AlbumPhoto values (AlbumID, lastPhotoID);
END//

DROP PROCEDURE IF EXISTS insertOrder//
create procedure insertOrder(in CustomerID INT(10), in ProducerID INT(10),in PhotographerID INT(10), in Payed INT(10))
  BEGIN
  insert into `Order` values (null, CustomerID, ProducerID,PhotographerID, Payed);
END//

DROP PROCEDURE IF EXISTS insertNewProduct//
create procedure insertNewProduct(in ProducerID INT(10), in ProductName VARCHAR(20), in Description MEDIUMTEXT, in MinimumPrice Decimal(5, 2))
  BEGIN
  DECLARE lastProductID INT;
  insert into Product values (null, ProducerID, ProductName, Description, MinimumPrice);
  set lastProductID = (select MAX(ProductID) from Product);
  CALL insertProductToPhotographerProduct(lastProductID, '0.0');
END//


DROP PROCEDURE IF EXISTS insertProductToPhotographerProduct//
create procedure insertProductToPhotographerProduct(in ProductID INT(10), in ProfitPrice DECIMAL(5, 2))
  BEGIN
  DECLARE lastProductID INT;
  insert into PhotographerProduct (PhotographerProductID, ProductID, PhotographerID, ProfitPrice) select null, ProductID, ph.PhotographerID, ProfitPrice from Photographer ph, Producer p where ph.producerID = p.ProducerID;
END//

DROP PROCEDURE IF EXISTS insertProductCopy//
create procedure insertProductCopy(in ProductID INT(10), in PhotoID INT(10), in FilterID INT(10))
  BEGIN
  DECLARE maxOrderID int;
  set maxOrderID = (select Max(OrderID) from `Order`);
  insert into ProductCopy values (ProductID, PhotoID, FilterID, maxOrderID);
END//

DROP PROCEDURE IF EXISTS insertFilter//
create procedure insertFilter(in Name VARCHAR(50))
  BEGIN
  insert into Filter values (null, Name);
END//

DROP PROCEDURE IF EXISTS createAlbum//
create procedure createAlbum(in PhotographerID INT(10), in CustomerID INT(10), in AlbumName VARCHAR(30))
  BEGIN
  insert into Album values (null, PhotographerID, CustomerID, AlbumName);
END//

DROP PROCEDURE IF EXISTS updateMinimumPriceFromProduct//
create procedure updateMinimumPriceFromProduct(in iProductID INT(10), in Price DECIMAL(5,2))
  COMMENT 'update the minimum price of a product.'
  BEGIN
    start transaction;
    update Product set MinimumPrice = Price where ProductID = iProductID;
    commit;
END//

DROP PROCEDURE IF EXISTS updateProfitPriceFromPhotographerProduct//
create procedure updateProfitPriceFromPhotographerProduct(in iPhotographerProductID INT(10), in Price DECIMAL(5,2))
  COMMENT 'update the profit price of a PhotographerProduct.'
  BEGIN
    start transaction;
    update PhotographerProduct set ProfitPrice = Price where PhotographerProductID = iPhotographerProductID;
    commit;
END//

DROP PROCEDURE IF EXISTS getAllAlbumsFromUser//
create procedure getAllAlbumsFromUser(in iCustomerID INT(10))
  COMMENT 'get all album ids from user.'
  BEGIN
    start transaction;
    select * from Album a, AlbumPhoto ap, Photo p 
	where a.CustomerID = iCustomerID
    AND a.AlbumID = ap.AlbumID
    AND ap.PhotoID = p.PhotoID;
    commit;
END//

DROP PROCEDURE IF EXISTS getAlbumId//
create procedure getAlbumId(in iAlbumID INT(10))
  COMMENT 'get all photos from an album.'
  BEGIN
    start transaction;
    select * from AlbumPhoto ap, Photo p 
	where ap.AlbumID = iAlbumID
	AND ap.PhotoID = p.PhotoID;
    commit;
END//

DROP PROCEDURE IF EXISTS checkLogin//
create procedure checkLogin(IN iUsername VARCHAR(50), IN iPassword VARCHAR(50), IN iType VARCHAR(15), OUT oLoginValue INT, OUT oUserID INT)
  BEGIN    
  DECLARE UserID INT;
    start transaction;
    set UserID = (select u.UserID from User u where u.Username = iUsername);
    IF (iType = 'Producer') then 
    IF EXISTS(select * from User u, Producer p where u.UserID = p.ProducerID AND Username = iUsername AND Password = iPassword)then
      set oLoginValue = 1;
      set oUserID = UserID;
    else 
      set oLoginValue = 0;
      set oUserID = UserID;      
    END IF;
  ELSEIF (iType = 'Photographer') then
    IF EXISTS(select * from User u, Photographer p where u.UserID = p.PhotographerID AND Username = iUsername AND Password = iPassword)then
      set oLoginValue = 1;
      set oUserID = UserID;
    else 
      set oLoginValue = 0;
      set oUserID = UserID;   
    END IF;
    ELSEIF (iType = 'Customer') then
    IF EXISTS(select * from User u, Customer c where u.UserID = c.CustomerID AND Username = iUsername AND Password = iPassword)then
      set oLoginValue = 1;
      set oUserID = UserID;
    else 
      set oLoginValue = 0;
      set oUserID = UserID;   
    END IF;
  ELSE
    set oLoginValue = 0;
    set oUserID = 0;
    END IF;
    commit;
END//

DROP PROCEDURE IF EXISTS login//
create procedure login(IN iUsername VARCHAR(50), IN iPassword VARCHAR(50), IN iType VARCHAR(15))
  BEGIN    
  CALL checkLogin(iUsername, iPassword, iType, @loginCode, @uid);
  SELECT @loginCode, @uid;
  commit;
END//

 DROP PROCEDURE IF EXISTS getPhotographerAlbumId//
create procedure getPhotographerAlbumId(in PhotographerID INT(10))
  COMMENT 'get all album ids from photographer.'
  BEGIN
    start transaction;
    select * from Album a
	where a.PhotographerID = PhotoGrapherID;
    commit;
END//

 DROP PROCEDURE IF EXISTS getAlbumCustomerId//
create procedure getAlbumCustomerId(in CustomerID INT(10))
  COMMENT 'get all albums from customer id.'
  BEGIN
    start transaction;
    select * from Album a
	where a.CustomerID = CustomerID;
    commit;
END//

DROP PROCEDURE IF EXISTS generateCodeForAlbum//
create procedure generateCodeForAlbum(IN iAlbumID VARCHAR(50))
  BEGIN    
  DECLARE CustomerID int;
    start transaction;
    set CustomerID = (select a.CustomerID from Album a where a.AlbumID = iAlbumID);
    select concat(CustomerID, '_', iAlbumID) as 'AlbumCode';
    commit;
END//

DROP PROCEDURE IF EXISTS assignCustomertoAlbum//
create procedure assignCustomertoAlbum(IN iAlbumID INT(10), IN iCustomerID INT(10))
  BEGIN    
	insert into customeralbum values (iCustomerID, iAlbumID);
END//
