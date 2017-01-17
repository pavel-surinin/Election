var DistrictRepresentativeCreateFormComponent = React.createClass({
  render: function() {
    return (
      <form>
        <div className="form-heading">
          <h2> Registruoti/naujinti apylinkės atstovą </h2>
        </div>
        <div className="form-group">
            <label for="usrName">Vardas:</label>
            <input type="text" className="form-control" id="usrName"/>
        </div>
        <div className="form-group">
            <label for="usrLastName">Pavardė:</label>
            <input type="text" className="form-control" id="usrLastName"/>
        </div>
        <div className="form-group">
          <label for="Selectcounty">Atstovaujama apylinkė:</label>
          <select className="form-control" id="Selectcounty">
            <option>Apylinkė 1</option>
            <option>Apylinkė 2</option>
            <option>Apylinkė 3</option>
            <option>Apylinkė 4</option>
          </select>
        </div>
        <div className="form-group">
          <a className="btn btn-success btn-sm" href="#" role="button">Registruoti</a>
          <a className="btn btn-warning btn-sm" href="#/rep" role="button">Atšaukti</a>
        </div>
      </form>
    );
  }

});

window.DistrictRepresentativeCreateFormComponent = DistrictRepresentativeCreateFormComponent;
