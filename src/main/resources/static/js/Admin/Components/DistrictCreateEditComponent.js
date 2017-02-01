var DistrictCreateEditComponent = React.createClass({

  render: function() {
    var nameErrorMesages = validation.showMsg(this.props.nameErrorMesages);
    var adressErrorMesages = validation.showMsg(this.props.adressErrorMesages);
    var existsErrorMesages = validation.showMsg(this.props.existsErrorMesages);
    var counties = [];
    this.props.countyList.map(function(county,index) {
      counties.push(
        <option key={index} value={county.id}>{county.name}</option>
      );
    });

    return (
              <div className="row">
                <div className="col-md-6 col-md-offset-3 col-xs-12">
                  <div className="panel panel-primary">
                    <div className="panel-heading text-center">
                      <h4><b> Registruoti apylinkę </b></h4>
                    </div>
                        <div className="panel-body">
                          <div className="form-heading">
                          </div>
                          <form onSubmit={this.props.onHandleSubmit} role="form">
                          <div className="input-group col-xs-12 text-primary">
                          <label> Apylinkės pavadinimas </label>
                            <input
                              id="name-input"
                              type="text"
                              onChange={this.props.onHandleNameChange}
                              value={this.props.name}
                              className="form-control"
                              placeholder="Apylinkės pavadinimas"
                              required
                            />
                          </div>
                          <br/>
                          {nameErrorMesages}
                          <div className="input-group col-xs-12 text-primary">
                          <label>Apylinkės Adresas</label>
                            <input
                              id="adress-input"
                              type="text"
                              onChange={this.props.onHandleAdressChange}
                              value={this.props.adress}
                              className="form-control"
                              placeholder="Apylinkės Adresas"
                              required
                            />
                          </div><br/>
                          {adressErrorMesages}
                          <div className="form-group text-primary">
                            <label>Apygarda, kuriai priklauso apylinkė:</label>
                            <select value={this.props.county} onChange={this.props.onHandleCountyChange} id="county-district-select" className="form-control" required>
                              {counties}
                            </select>
                          </div><br/>
                          {existsErrorMesages}
                          <div className='text-center'>
                            <button className='btn btn-success btn-outline col-xs-5' id="create-button">
                              {this.props.action}
                            </button>
                          <a className="btn btn-danger btn-outline col-xs-5 col-xs-offset-2" id="cancel-button" href="#/admin/district" role="button">Atšaukti</a>
                          </div>
                          </form>
                      </div>
                    </div>
                </div>
              </div>
    );
  }
});

window.DistrictCreateEditComponent = DistrictCreateEditComponent;
