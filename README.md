# Neighbours

Neigbours is an Android app created for class at the University of Novi Sad. It is used as tool to search and post multiple choice questions in the surrounding area. Anyone in the searched diameter can see and solve questions that are posted in that area. 

## Authors

Milena Lalic   
Svetlana Djuric    
Danilo Zekovic    

## Running the app

install mongodb

make folder data-mongodb in git\neighbours\educon

cd git\neighbours\educon\data-mongodb

run command:  mongod --dbpath=.


run spring application educon

check your ip adress (192.168.x.x.) 

change String BASE_URL = "http://192.168.x.x:8095/educon/"   in both classes in package retrofit

run android application EducationConsultant  (if user uses Android Studio - it is recommended to be installed the latest version)
