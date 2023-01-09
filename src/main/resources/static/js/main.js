//trending crypto  *************************************

const tbody = document.querySelector("#trending-stock")

const getCrypto = async () => {
    const request = await fetch("https://api.coingecko.com/api/v3/search/trending");
    const data = await request.json();
    return data;
};

getCrypto().then(data => {
    let stockData = data.coins
    console.log(stockData)
    let html = ''
    const stock = stockData.map(el => {

        html += `
    <tr>
        <td>${el.item.name}</td>
        <td>${el.item.symbol}</td>
        <td>${el.item.market_cap_rank}</td>
      
    </tr>`

        tbody.innerHTML = html




    })

});
//********************************************************


//Crypto list***********************************************


const cryptoList = async () => {
    const request = await fetch("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false&price_change_percentage=24h");
    const data = await request.json();
    return data;
};

cryptoList().then(data => {
    console.log(data)
    let html = ''
    let listData = data.forEach(item => {

        let priceChange = item.price_change_24h.toFixed(2)

        html += `
    <tr>
    <td>
    <div class="row">
    <div class="col-2"><span><img style="width: 20px; height: 20px" src="${item.image}" alt=""></span></div>
     <div class="col">${item.name}</div>
</div>
  </td>   
  
  <td>${item.current_price}</td>   
   <td>${item.market_cap}</td> 
   <td>${item.high_24h}</td>
   <td>${item.low_24h}</td> 
   <td>${priceChange}</td> 
   
   <td>
   <form method="get" action="/addcrypto/${item.symbol}">
        <input hidden name="name" value="${item.name}">
        <input hidden name="price" value="${item.current_price}">
        <button type="submit">Add to portfolio</button>
    </form>
     
  
</td>
          
</tr>`

        //Original Add to portfolio link
        //     < a
        // href = "addCrypto/${item.current_price}/${item.name}/${item.symbol}"
        // className = "btn btn-primary" > Add
        // to
        // portfolio < /a>

        document.querySelector("#list-crypto").innerHTML = html


    })


})


//    Search Crypto**********************************************************************************


const searchCrypto = document.querySelector("#crypto-search")


const searchBtn = document.querySelector("#searchBtn")

searchBtn.addEventListener("click", function () {

    const cryptoSearch = searchCrypto.value
    console.log(cryptoSearch)

    const search = async () => {
        const request = await fetch(`https://api.coingecko.com/api/v3/coins/${cryptoSearch}?localization=false`);
        return await request.json();


    };


    search().then(data => {
        console.log(data)
        let newData = data
        let html = ''

        html += `
<h3 class="text-center text-primary mt-3">${newData.name} (${newData.symbol})</h3>
<div class="row">

<div class="col-8">
<div style="float: left;
 padding: 0 20px 20px 0;" class="s-img"> <img style="width: 10vw; height: 15vh" src="${newData.image.large}" class="card-img-top" alt="..."></div>
<div class="s-dis"><p>${newData.description.en}</p>   </div>

<hr>

    <a href="addCrypto/${newData.market_data.current_price.usd}/${newData.name}/${newData.symbol}" class="btn btn-primary">Add to portfolio</a>

 </div>
 
  <div class="col-4">
  
  <div class="card">
  <div class="card-body">
  <ul class="list-group list-group-flush">
   <li class="list-group-item">Current price: <span class="text-primary mx-1">$${newData.market_data.current_price.usd}</span></li>
      <li class="list-group-item">Genesis date: <span class="text-primary mx-4">${newData.genesis_date}</span></li>
      <li class="list-group-item">Sentiment down vote: <span class="text-primary mx-4"> ${newData.sentiment_votes_down_percentage}</span></li>
      <li class="list-group-item">Sentiment up Vote: <span class="text-primary mx-4">${newData.sentiment_votes_up_percentage}</span></li>
      <li class="list-group-item">Public interest score: <span class="text-primary mx-4">${newData.public_interest_score}</span></li>
  <li class="list-group-item">Community Score: <span class="text-primary mx-4">${newData.community_score}</span></li>
   <li class="list-group-item">Market cap: <span class="text-primary mx-4">${newData.market_data.market_cap.usd}</span></li>
   <li class="list-group-item">Circulating supply: <span class="text-primary mx-4">${newData.market_data.circulating_supply}</span></li>
   <li class="list-group-item">Max supply: <span class="text-primary mx-4">${newData.market_data.max_supply}</span></li>
   <li class="list-group-item">7 Days price change %: <span class="text-primary mx-4">${newData.market_data.price_change_percentage_7d}</span></li>
      <li class="list-group-item">30 Days price change %: <span class="text-primary mx-4">${newData.market_data.price_change_percentage_30d}</span></li>
         <li class="list-group-item">1 year price change %: <span class="text-primary mx-4">${newData.market_data.price_change_percentage_1y}</span></li>

 </ul>


  </div> 
</div>
  </div>
</div>

 `

        document.querySelector(".crypto-page").innerHTML = html

    })


})

