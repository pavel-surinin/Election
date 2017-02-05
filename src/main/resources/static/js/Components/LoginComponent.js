var LoginComponent = React.createClass({
  render: function() {
    var isLoggedMessage =
    (<div className="alert alert-info" role="alert">Jūs jau prisijungęs.
    <br/>
    <button className="btn btn-info" onClick={this.props.onHandleLogoutClick}>
      Atsijungti
    </button> </div>);
    if (this.props.isLogged) {
      return isLoggedMessage;
    } else {
      var message = '';
      if (this.props.wrongAuth) {
        message = (
          <div className="alert alert-danger" role="alert">
          <strong>Dėmesio!</strong> Blogai įvestas prisijungimo vardas arba slaptažodis.
          </div>
        );
      } else {
        message = '';
      }
      return (
        <div className="container vertical-center">
          <div className="row">
            <div className="col-md-4 col-md-offset-4">
              <div className="login-panel panel panel-default">
                <div className="panel-body">
                  <form onSubmit={this.props.onHandleSubmit}>
                    <div className="form-group">
                      <label>Prisijungimo vardas:</label>
                      <input onChange={this.props.onHandleUsernameChange} className="form-control"/>
                    </div>
                    <div className="form-group">
                      <label>Slaptažodis:</label>
                      <input onChange={this.props.onHandlePasswordChange} type="password" className="form-control" id="pwd"/>
                      </div>
                    <button type="submit" className="btn btn-default">Prisijungti</button>
                  </form>
                  {message}
                </div>
              </div>
            </div>
          </div>
        </div>
      );
    }
  }

});

window.LoginComponent = LoginComponent;
