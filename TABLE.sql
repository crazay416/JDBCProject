-- “WritingGroups” table holds the GroupName, Headwriter, YeardFormed, and Subject
CREATE TABLE WritingGroup(
-- Unique name of each writing group
    GroupName VARCHAR(50) NOT NULL,
-- Name of the group’s head writer
    HeadWriter VARCHAR(50) NOT NULL,
-- Year when the group was formed	
    YearFormed INT NOT NULL,	
-- Subject’s name of the group		
    Subject VARCHAR(50) NOT NULL,		
    
    CONSTRAINT writingGroups_pk PRIMARY KEY (GroupName));

-- “Publishers” table holds the PublisherName, PublisherAddress, PublisherPhone and PublisherEmail
CREATE TABLE Publishers(
-- Unique name of the publisher
    PublisherName VARCHAR(50) NOT NULL,
-- Address of the publisher
    PublisherAddress VARCHAR(50) NOT NULL,
-- Phone number of the publisher
    PublisherPhone VARCHAR(13) NOT NULL,
-- Email address of the publisher
    PublisherEmail VARCHAR(50) NOT NULL,
    
    CONSTRAINT publishers_pk PRIMARY KEY (PublisherName));

-- “Books” table holds the GroupName, BookTitle, PublisherName, YearPublished, and NumberPages
CREATE TABLE Books(
-- Name of the writing group
    GroupName VARCHAR(50) NOT NULL,
-- Unique title of the book
    BookTitle VARCHAR(50) NOT NULL,
-- Name of the publisher
    PublisherName VARCHAR(50) NOT NULL,
-- Year when the book was published
    YearPublished INT NOT NULL,
-- Total pages of the book
    NumberPages INT NOT NULL,
    
    CONSTRAINT books_writingGroup_01 FOREIGN KEY (GroupName)
    REFERENCES WritingGroup (GroupName),
    CONSTRAINT books_publishers_01 FOREIGN KEY (PublisherName)
    REFERENCES Publishers (PublisherName),

    CONSTRAINT books_pk PRIMARY KEY (GroupName, BookTitle),
    CONSTRAINT books_uk_01 UNIQUE (BookTitle, PublisherName));
