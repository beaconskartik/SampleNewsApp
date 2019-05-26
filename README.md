
1) I have tried to implement MVVM model and you will find that the data coming from server is abstracted in newsconnector whose responsibilty is talk to server and provide the data model. It also take care of parsing json response and any error handling.

2) When there is network available, it will retry to fetch data and show it user.

3) NewsListFragment will show the news in the form of list and provide click handling via interface

4) DetailNewsFragment will show the detail news (Data is provided by VmDetailNews)

5) Interaction to newsconnector is done by View Model (VmNews)

6) Unit test cases of API availabity, retorfit API, newsconnector API, NewsEntity and MediaEntity are in newsconnector module

7)ViewModel and utils unit test cases are inside app.
