const port_to_serve = 3000
const routingTable = [
    {"name" : "/registry" , "url" : "./registerPage/index"},
    {"name" : "/login" , "url" : "./loginPage/index"},
    //{"name" : "/blank" , "url" : "./blankPage/index"},
    {"name" : "/edit" , "url" : "./blogEditorPage/index"},
    {"name" : "/about" , "url" : "./aboutPage/index"},
    {"name" : "/404" , "url" : "./404Page/index"},
    {"name" : "/home" , "url" : "./homePage/index"}
    ]



const launchProxyService = require('./__proxyServer__/proxyServer');

module.exports=()=>{launchProxyService(routingTable, port_to_serve)}
