
async function blank() {
            await axios.post("/blank/status/",{
                params: {ID: 123},
                paramsSerializer: function(params) {
                    return Qs.stringify(params, {arrayFormat: 'brackets'})
                  }
            })
            /*
            .then(
            ()=>{location.assign("/blank/status/")}
            )
            */
            return true
}
