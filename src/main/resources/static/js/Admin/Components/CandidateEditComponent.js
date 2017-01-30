var CandidateEditComponent = React.createClass({

  render: function() {
    return (
        <div className="panel panel-default">
          <div className="panel-body">
            <form onSubmit={this.props.onHandleSubmit}>
              <div className="form-group">
                <label>Vardas</label>
                <input onChange={this.props.onHandleNameChange} className="form-control"/>
              </div>
              <div className="form-group">
                <label>Pavardė</label>
                <input onChange={this.props.onHandleSurnameChange} className="form-control"/>
              </div>
              <div className="form-group">
                <label>Gimimo data</label>
                <input onChange={this.props.onHandleDateChange} className="form-control"/>
              </div>
              <div className="form-group">
                <label>Partija</label>
                <div className="dropdown">
                  <button onChange={this.props.onHandlePartyChange} className="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                  Pasirinkite
                    <span className="caret"></span>
                  </button>
                    <ul className="dropdown-menu" aria-labelledby="dropdownMenu1">
                      <li><a href="#">TS-LKD</a></li>
                      <li><a href="#">LSDP</a></li>
                      <li><a href="#">LRLS</a></li>
                      <li role="separator" class="divider"></li>
                      <li><a href="#">Separated link</a></li>
                    </ul>
                  </div>
                  <div className="form-group">
                    <label for="comment">Aprašymas</label>
                    <textarea input onChange={this.props.onHandleCommentChange} className="form-control" rows="5" id="comment"></textarea>
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

window.CandidateEditComponent = CandidateEditComponent;
