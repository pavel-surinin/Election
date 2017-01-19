var DistrictCreateEditComponent = React.createClass({

  render: function() {
    return (
        <div>
          <div className="container vertical-center">
            <div className="row">
              <div className="col-md-4 col-md-offset-4">
                <div className="login-panel panel panel-default">
                  <div className="panel-body">
                    <form role="form">
                      <div className="input-group">
                        <label> Apylinkės pavadinimas </label>
                        <input
                          type="text"
                          onChange={this.props.onHandleNameChange}
                          value={this.props.name}
                          className="form-control"
                          placeholder="Apylinkės pavadinimas"
                          required
                        />
                      </div>
                        <div><br /></div>
                      <div className="input-group">
                        <label> Apylinkės adresas </label>
                        <input
                          type="text"
                          onChange={this.props.onHandleAdressChange}
                          value={this.props.adress}
                          className="form-control"
                          placeholder="Adresas"
                          required
                        />
                      </div>
                        <div><br /></div>
                      <div className="form-group">
                        <label>Apygarda:</label>
                        <select value={this.props.county} onChange={this.props.onHandleCountyChange} className="form-control">
                          <option value="1">Apylinkė 1</option>
                          <option value="2">Apylinkė 2</option>
                          <option value="3">Apylinkė 3</option>
                          <option value="4">Apylinkė 4</option>
                        </select>
                      </div>
                        <div><br /></div>
                    </form>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
    );
  }

});

window.DistrictCreateEditComponent = DistrictCreateEditComponent;
