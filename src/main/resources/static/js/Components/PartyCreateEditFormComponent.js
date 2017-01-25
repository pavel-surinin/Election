var PartyCreateEditFormComponent = React.createClass({

  render: function() {
    console.log(this.props);
    var message = '';
    if (this.props.nameClone) {
      message = (
          <div className="alert alert-danger" role="alert">
          <strong>Dėmesio!</strong> Tokia partija jau užregistruota.
          </div>
        );
    } else {
      message = '';
    }
    return (
		      <div className="container vertical-center">
		        <div className="row">
		          <div className="col-md-6 col-md-offset-1">
		            <div className="panel panel-default">
		              <div className="panel-body">
		                <form onSubmit={this.props.onHandleSubmit} role="form">
		                <div className="input-group">
		                  <label>Partijos Pavadinimas</label>
		                    <input
		                      type="text"
		                      onChange={this.props.onHandleNameChange}
		                      value={this.props.name}
		                      className="form-control"
		                      placeholder="Pavadinimas"
		                      required
		                    />
		                </div><br/>
		                    <div className="form-group">
		                      <label>Pasirinkite failą</label>
		                      <input type="file" id="exampleInputFile" />
		                      <p className="help-block">Pasirinkite ir įkelkite partijos sąrašo failą CSV formatu </p>
		                  </div><br/>
		                  <button className='btn btn-success btn-block'>
		                    {this.props.action}
		                  </button>
		                </form>
                    {message}
		                  <div>
		                    <a className="btn btn-danger btn-block" href="#/admin/party" role="button">Atšaukti</a>
		                  </div>
		              </div>
		            </div>
		          </div>
		        </div>
		      </div>
    );
  }
});

window.PartyCreateEditFormComponent = PartyCreateEditFormComponent;
