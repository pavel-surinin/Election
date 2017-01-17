var DistrictCreateEditComponent = React.createClass({

  render: function() {
    return (
        <div className="panel panel-default">
          <div className="panel-body">
            <form onSubmit={this.props.onHandleSubmit}>
              <div className="form-group">
                <label>Pavadinimas</label>
                <input onChange={this.props.onHandleTitleChange} className="form-control"/>
              </div>
              <div className="form-group">
                <label>Apygardos</label>
                <div className="dropdown">
                  <button onChange={this.props.onHandleCountyChange} className="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                  Pasirinkite
                    <span className="caret"></span>
                  </button>
                    <ul className="dropdown-menu" aria-labelledby="dropdownMenu1">
                      <li><a href="#">Žirmūnai</a></li>
                      <li><a href="#">Senamiestis</a></li>
                      <li><a href="#">Antakalnis</a></li>
                      <li role="separator" class="divider"></li>
                      <li><a href="#">Separated link</a></li>
                    </ul>
                  </div>
                </div>
                <div className="form-group">
                <label>Atstovai</label>
                <div className="dropdown">
                  <button onChange={this.props.onHandleRepresentativesChange} className="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                  Pasirinkite
                    <span className="caret"></span>
                  </button>
                    <ul className="dropdown-menu" aria-labelledby="dropdownMenu1">
                      <li><a href="#">Jonaitis</a></li>
                      <li><a href="#">Petraitis</a></li>
                      <li><a href="#">Antanaitis</a></li>
                      <li role="separator" class="divider"></li>
                      <li><a href="#">Separated link</a></li>
                    </ul>
                  </div>
                </div>
              <button onHandle={this.props.onHandleSubmit} type="submit" className="btn btn-primary">Išsaugoti</button>
              <button onHandle={this.props.onHandleCancel} type="cancel" className="btn btn-danger">Atšaukti</button>
            </form>
          </div>
        </div>
    );
  }

});

window.DistrictCreateEditComponent = DistrictCreateEditComponent;
