var HomeComponent = React.createClass({

  render: function() {
    document.title='Rinkimai 2017';
    return (
      <div>
        <div className="jumbotron">
          <h1>Sveiki atvyke į rinkimų puslapį</h1>
          <p>Čia galima peržiūrėti 2017m. Seimo rinkimų balsavimo rezultatus</p>
          <p><a id="results-button" className="btn btn-primary btn-lg" href="results/csv" role="button">Rezultatai</a></p>



        </div>
      </div>
    );
  }

});

window.HomeComponent = HomeComponent;
