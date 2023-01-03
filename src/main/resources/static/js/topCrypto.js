

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