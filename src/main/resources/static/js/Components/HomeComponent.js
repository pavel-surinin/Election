var HomeComponent = React.createClass({

  render: function() {
    return (
      <div>
        <div className="jumbotron">
          <h1>Sveiki atvyke į rinkimų puslapį</h1>
          <p>Čia galima pažiūrėt rezultatus ir tralialia</p>
          <p><a className="btn btn-primary btn-lg" href="#" role="button">Rezultatai</a></p>
        </div>
      </div>
    );
  }

});

window.HomeComponent = HomeComponent;
