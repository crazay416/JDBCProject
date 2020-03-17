# WritingGroup holds the GroupName, Headwriter, YeardFormed, and Subject
CREATE TABLE WritingGroup(
    GroupName VARCHAR(255) NOT NULL,
    HeadWriter VARCHAR(255)NOT NULL,
    YearFormed INT NOT NULL,
    Subject VARCHAR(255) NOT NULL,
    
    CONSTRAINT writinggroups_pk PRIMARY KEY (GroupName));

# Publishers holds the Publisher's name, address, phone number and email
CREATE TABLE Publishers(
    PublisherName VARCHAR(255) NOT NULL,
    PublisherAddress VARCHAR(255) NOT NULL,
    PublisherPhone VARCHAR(255) NOT NULL,
    PublisherEmail VARCHAR(255) NOT NULL,
    
    CONSTRAINT publishers_pk PRIMARY KEY (PublisherName));
# Books hold the writing group name, book title, the publisher, year that it
# was published and the number of pages in that book
CREATE TABLE Books(
    WritingGroupName VARCHAR(255) NOT NULL,
    BookTitle VARCHAR(255) NOT NULL,
    BookPublisherName VARCHAR(255) NOT NULL,
    YearPublished INT NOT NULL,
    NumberPages INT NOT NULL,
    
    CONSTRAINT writinggroups_01 FOREIGN KEY (WritingGroupName)
    REFERENCES WritingGroup (GroupName),

    CONSTRAINT publishers_01 FOREIGN KEY (BookPublisherName)
    REFERENCES Publishers (PublisherName),

    CONSTRAINT books_pk PRIMARY KEY (WritingGroupName, BookTitle),

    CONSTRAINT books_01 UNIQUE (BookTitle, BookPublisherName));
