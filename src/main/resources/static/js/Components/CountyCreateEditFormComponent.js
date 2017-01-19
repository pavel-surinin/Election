var CountyCreateEditFormComponent = React.createClass({
  render: function() {
    return (
      <div>
        <form onSubmit={this.props.onHandleSubmit}>
          <div className="form-heading">
            <h2> Registruoti/naujinti apygardą </h2>
          </div>
          <div className="form-group">
              <label for="cntName">Apygardos pavadinimas:</label>
              <input onChange={this.props.onHandleNameChange} type="text" className="form-control" id="cntName"/>
          </div>
          <div className="form-group">
                <label>Apygardai priklausančių kandidatų sąrašas:</label>
                <button type="button" className="btn btn-info btn-sm" data-toggle="modal" data-target="#myModal">Kandidatų sąrašas</button>
                <div id="myModal" className="modal fade" role="dialog">
                  <div className="modal-dialog">
                    <div className="modal-content">
                      <div className="modal-header">
                        <button type="button" className="close" data-dismiss="modal">&times;</button>
                        <h4 className="modal-title">Pažymėkite apygardai priklausančius kandidatus</h4>
                      </div>
                      <div className="modal-body">
                        <div className="checkbox">
                          <label>
                            <input type="checkbox"/> Kandidatas
                          </label>
                        </div>
                      </div>
                      <div className="modal-footer">
                        <button type="button" className="btn btn-default" data-dismiss="modal">Uždaryti</button>

                      </div>
                    </div>
                  </div>
                </div>
          </div>
          <div className="form-group">
            <button type="submit" className="btn btn-success">Registruoti</button>

          </div>
        </form>
            <button type="cancel" className="btn btn-warning">Atšaukti</button>
      </div>
    );
  }

});

window.CountyCreateEditFormComponent = CountyCreateEditFormComponent;
