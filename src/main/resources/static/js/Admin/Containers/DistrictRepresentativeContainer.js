var DistrictRepresentativeContainer = React.createClass({

  getInitialState: function() {
    return {
      representativesList : [],
      isLoading : true,
    };
  },

  componentWillMount: function() {
    if (this.props.location.query.succesCreateText != null) {
      this.setState({succesCreateText : this.props.location.query.succesCreateText});
    }
    if (this.props.location.query.credentials != null) {
      this.setState({credentials : this.props.location.query.credentials});
    }
    EventEmitter.publish({ eventType: 'LogCounty' });
    var self = this;
    axios
      .get('/representative')
      .then(function(response){
        self.setState({
          representativesList :  response.data,
          isLoading : false,
        });
      })
      .catch(function(err){
        console.error('DistrictRepresentativeContainer.componentWillMount.axios.get.representative', err);
      });
  },

  render: function() {
    if (this.state.isLoading) {
      return (
        <div>
          <img src='./Images/loading.gif'/>
        </div>
      );
    } else {
      return (
        <DistrictRepresentativeComponent
          representativesList={this.state.representativesList}
          succesCreateText={this.state.succesCreateText}
          credentials={this.state.credentials}/>
      );
    }

  }

});

window.DistrictRepresentativeContainer = DistrictRepresentativeContainer;
