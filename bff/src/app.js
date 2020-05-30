const mount = require('koa-mount');
const koa = require('koa');
const url = require('url')

const app = new koa;

app.use(async (ctx, next)=> {
    const parsedUrl = url.parse(ctx.url);
    if (
        (parsedUrl.pathname === '/register') || 
        (parsedUrl.pathname === '/login') || 
        (parsedUrl.pathname === '/blank')
    ) {
        parsedUrl.pathname = parsedUrl.pathname + '/'
        ctx.redirect(url.format(parsedUrl));
        return
    }

    await next();
})

app.use(
    mount('/register', require('./registerPage/index'))
)

app.use(
    mount('/login', require('./loginPage/index'))
)

app.use(
    mount('/blank', require('./blankPage/index'))
)

app.listen(3000, ()=> {
   console.log(`----- Noisete running on process < ${process.pid} > `)
   console.log('----- Listening port 3000')
});