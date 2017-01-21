var CountyCreateEditFormComponent = React.createClass({
  render: function() {
    var message = '';
    if (this.props.nameClone) {
      message = (
        <div className="alert alert-warning alert-dismissible" role="alert">
        <button type="button" className="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <strong>Dėmesio!</strong> Apygarda su tokiu pavadinimu jau užregistruota.
        </div>
      );
    } else {
      message = '';
    }
    return (

      <div className="container vertical-center">
        <div className="row">
          <div className="col-md-6 col-md-offset-1 col-xs-6">
            <div className="panel panel-default">
              <div className="panel-body">
                <div className="form-heading">
                <form onSubmit={this.props.onHandleSubmit} role="form">
                <div className="input-group">
                <label>Apygardos Pavadinimas</label>
                  <input
                    type="text"
                    onChange={this.props.onHandleNameChange}
                    value={this.props.name}
                    className="form-control"
                    placeholder="Pavadinimas"
                    id="inputError"
                    required
                  />
                </div><br/>
                <button className='btn btn-success btn-block'>
                  {this.props.action}
                </button>
                </form>
                <div>
                  <a className="btn btn-danger btn-block" href="#/admin/county" role="button">Atšaukti</a>
                </div>
                <br/>
                {message}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    );
  }

});

window.CountyCreateEditFormComponent = CountyCreateEditFormComponent;
