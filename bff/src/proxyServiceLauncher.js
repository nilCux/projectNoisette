const port_to_serve = 3000
const routingTable = [
    {"name" : "/registry" , "url" : "./registerPage/index"},
    {"name" : "/login" , "url" : "./loginPage/index"},
    {"name" : "/blank" , "url" : "./blankPage/index"},
    {"name" : "/" , "url" : "./homePage/index"}
    ]



const launchProxyService = require('./__proxyServer__/proxyServer');

module.exports=()=>{launchProxyService(routingTable, port_to_serve)}