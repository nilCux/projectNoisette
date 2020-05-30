const session = require('koa-session');
const Koa = require('koa');
const app = new Koa();

const bpp = new Koa()
bpp.use(function (ctx, next){
  console.log("Hello")
  next();
}) 
bpp.use((ctx)=>{
    console.log("world")
  })

bpp.listen(3000);
console.log('listening on port 3000');