var DistrictRepresentativeContainer = React.createClass({

  getInitialState: function() {
    return {
      representativesList : [],
      isLoading : true,
    };
  },

  componentWillMount: function() {
    console.log(this.props);
    if (this.props.location.query.succesCreateText != null) {
      this.setState({succesCreateText : this.props.location.query.succesCreateText});
    }
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
      console.log(this.state.representativesList);
      return (
        <DistrictRepresentativeComponent
          representativesList={this.state.representativesList}
          succesCreateText={this.state.succesCreateText}/>
      );
    }

  }

});

window.DistrictRepresentativeContainer = DistrictRepresentativeContainer;
