/***********************************************************************/
/*********************** Global Parameters *****************************/
/***********************************************************************/



/***********************************************************************/
/***********************************************************************/

const mount = require('koa-mount');
const koa = require('koa');
const url = require('url')
const app = new koa;
const helmet = require("koa-helmet");

function launchProxyService(routingTable, port_to_serve) {
var proxyList = function() {
    container = new Array()
    routingTable.forEach((element)=>{container[container.length] = element.name})
    return container;
}();

app.use(helmet());

app.use(async (ctx, next)=> {
    const parsedUrl = url.parse(ctx.url);
    if (parsedUrl.pathname == '/') {
        parsedUrl.pathname = '/home/'
        ctx.redirect(url.format(parsedUrl));
    }
    
    if (proxyList.includes(parsedUrl.pathname) && parsedUrl.pathname!=='/') {
        parsedUrl.pathname = parsedUrl.pathname + '/'
        ctx.redirect(url.format(parsedUrl));
        return
    } else {
        await next()
        if (ctx.response.status === 404) {
            parsedUrl.pathname = '/404/'
        ctx.redirect(url.format(parsedUrl));
        }
    }

})

routingTable.forEach(element=>app.use(mount(element.name, require('.'+element.url))))

/*
app.use(
    mount('/register', require('../registerPage/index'))
)

app.use(
    mount('/login', require('../loginPage/index'))
)

app.use(
    mount('/blank', require('../blankPage/index'))
)
*/
app.listen(port_to_serve, ()=> {
   console.log(`----- Noisete running on process < ${process.pid} > `)
   console.log('----- Listening port 3000')
});
}

module.exports = launchProxyService
