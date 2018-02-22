insert into dbo.Inventory 
(
[ItemName],
[QTY]
,[ExpireDate],
[DateEntered],
[Notes],
[LastUpdated],
[IsDeleted],
[Category]
) 
values
(
'Carrots',
6,
'2018-03-05',
getdate(),
'This is one sick beet',
GETDATE(),
0,
'Produce'
)

insert into dbo.Inventory 
(
[ItemName],
[QTY]
,[ExpireDate],
[DateEntered],
[Notes],
[LastUpdated],
[IsDeleted],
[Category]
) 
values
(
'Ribeye',
3,
'2018-02-26',
getdate(),
'This is a note about meat',
GETDATE(),
0,
'Meat'
)

insert into dbo.Inventory 
(
[ItemName],
[QTY]
,[ExpireDate],
[DateEntered],
[Notes],
[LastUpdated],
[IsDeleted],
[Category]
) 
values
(
'2% Milk',
5,
'2018-02-26',
getdate(),
'gallons',
GETDATE(),
0,
'Dairy'
)

insert into dbo.Inventory 
(
[ItemName],
[QTY]
,[ExpireDate],
[DateEntered],
[Notes],
[LastUpdated],
[IsDeleted],
[Category]
) 
values
(
'Goat cheese',
2,
'2018-02-26',
getdate(),
'containers',
GETDATE(),
0,
'Dairy'
)

insert into dbo.Inventory 
(
[ItemName],
[QTY]
,[ExpireDate],
[DateEntered],
[Notes],
[LastUpdated],
[IsDeleted],
[Category]
) 
values
(
'Wild Rice',
1,
'2019-02-26',
getdate(),
'This is a note about meat',
GETDATE(),
0,
'Non-Parishable'
)

insert into dbo.Inventory 
(
[ItemName],
[QTY]
,[ExpireDate],
[DateEntered],
[Notes],
[LastUpdated],
[IsDeleted],
[Category]
) 
values
(
'Beets',
17,
'2018-03-25',
getdate(),
'This is one sick beet',
GETDATE(),
0,
'Produce'
)