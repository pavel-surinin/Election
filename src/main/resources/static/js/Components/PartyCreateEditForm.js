var PartyCreateEditForm = React.createClass({

  render: function() {
    return (
      <form>
        <div className="form-heading">
          <h2> Registruoti/naujinti partiją </h2>
        </div>
        <div className="form-group">
            <label for="partyName">Partijos Pavadinimas:</label>
            <input type="text" className="form-control" id="usrName"/>
        </div>
        <div className="form-group">
          <label for="exampleInputFile">Pasirinkite failą</label>
          <input type="file" id="exampleInputFile" />
          <p className="help-block">Pasirinkite ir įkelkite partijos sąrašo failą CSV formatu </p>
        </div>
        <div className="form-group">
          <a className="btn btn-success btn-sm" href="#" role="button">Registruoti</a>
          <a className="btn btn-warning btn-sm" href="#/party" role="button">Atšaukti</a>
        </div>
      </form>
    );
  }
});

window.PartyCreateEditForm = PartyCreateEditForm;
