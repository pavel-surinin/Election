var CountyCreateEditFormComponent = React.createClass({
  render: function() {
    return (
      <div className="container vertical-center">
        <div className="row">
          <div className="col-md-6 col-md-offset-1">
            <div className="login-panel panel panel-default">
              <div className="panel-body">
                <form onSubmit={this.props.onHandleSubmit} role="form">
                <div className="input-group">
                <label>Apygardos Pavadinimas</label>
                  <input
                    type="text"
                    onChange={this.props.onHandleNameChange}
                    value={this.props.name}
                    className="form-control"
                    placeholder="Pavadinimas"
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
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

});

window.CountyCreateEditFormComponent = CountyCreateEditFormComponent;
